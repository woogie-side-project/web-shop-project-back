package com.project.webshopproject.user;

import static com.project.webshopproject.security.JwtProvider.AUTHORIZATION_HEADER;
import static com.project.webshopproject.security.JwtProvider.BEARER_PREFIX;
import static com.project.webshopproject.security.JwtProvider.REFRESHTOKEN_HEADER;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.webshopproject.user.entity.Grade;
import com.project.webshopproject.user.entity.User;
import com.project.webshopproject.user.entity.UserLoginType;
import com.project.webshopproject.user.entity.UserStatus;
import com.project.webshopproject.user.dto.KakaoUserInfoDto;
import com.project.webshopproject.security.JwtProvider;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j(topic = "KAKAO Login")
@Service
@RequiredArgsConstructor
public class KakaoService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final JwtProvider jwtProvider;

    public KakaoUserInfoDto kakaoLogin(final String code, HttpServletResponse response)
            throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getToken(code);

        // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);

        // 3. 필요시에 회원가입
        boolean isNewUser = registerKakaoUserIfNeeded(kakaoUserInfo, response);

        // 4. JWT 토큰 반환
        String createAccessToken = jwtProvider.createAccessToken(kakaoUserInfo.getEmail());
        String createRefreshToken = jwtProvider.createRefreshToken(kakaoUserInfo.getEmail());

        // 헤더에 토큰 저장
        response.setHeader(AUTHORIZATION_HEADER, createAccessToken);
        response.setHeader(REFRESHTOKEN_HEADER, createRefreshToken);

        kakaoUserInfo.setNewUser(isNewUser);
        return kakaoUserInfo;
    }

    public boolean registerKakaoUserIfNeeded(final KakaoUserInfoDto kakaoUserInfo,
            HttpServletResponse response) {
        // DB 에 중복된 Kakao Id 가 있는지 확인
        Long kakaoId = kakaoUserInfo.getId();
        User kakaoUser = userRepository.findBySocialId(kakaoId).orElse(null);
        boolean isNewUser = false;

        if (kakaoUser == null) {
            // 카카오 사용자 email 동일한 email 가진 회원이 있는지 확인
            String kakaoEmail = kakaoUserInfo.getEmail();
            User sameEmailUser = userRepository.findByEmail(kakaoEmail).orElse(null);
            if (sameEmailUser != null) {
                kakaoUser = sameEmailUser;
                // 기존 회원정보에 카카오 Id 추가
                kakaoUser = kakaoUser.kakaoIdUpdate(kakaoId);
            } else {
                // 신규 회원가입
                // password: random UUID
                String password = UUID.randomUUID().toString();
                String encodedPassword = passwordEncoder.encode(password);

                // email: kakao email
                String email = kakaoUserInfo.getEmail();

                kakaoUser = new User(kakaoUser.getUsername(), kakaoUserInfo.getNickname(), email,
                        encodedPassword, "미설정", "미설정",
                        Grade.BASIC, UserLoginType.KAKAO, UserStatus.ACTIVE);

                kakaoUser.kakaoIdUpdate(kakaoId);
                isNewUser = true;
            }
            userRepository.save(kakaoUser);
        }
        if ("미설정".equals(kakaoUser.getPhoneNumber())) {
            isNewUser = true;
        }
        if ("미설정".equals(kakaoUser.getAddress())) {
            isNewUser = true;
        }
        return isNewUser;
    }

    public String getToken(final String code) throws JsonProcessingException {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com")
                //TODO path 수정 필요
                .path("/oauth/token")
                .encode()
                .build()
                .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        //TODO 클라이언트 키 수정 필요
        body.add("client_id", "kakaoClientKey");
        //TODO 서버 리다이렉트 URI 수정 필요
        body.add("redirect_uri", "userBaseUrl/v1/user/kakao/callback");
        body.add("code", code);

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(body);

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        return jsonNode.get("access_token").asText();
    }

    public KakaoUserInfoDto getKakaoUserInfo(final String accessToken)
            throws JsonProcessingException {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://kapi.kakao.com")
                //TODO path 수정 필요
                .path("/v2/user/me")
                .encode()
                .build()
                .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, BEARER_PREFIX + accessToken);
        headers.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(new LinkedMultiValueMap<>());

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();

        log.info("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);
        return KakaoUserInfoDto.of(id, nickname, email);
    }

    public void profileUpdate(final String accessToken, final String nickname,
            final String phoneNumber, final String address) {
        String email = jwtProvider.extractEmail(jwtProvider.substringToken(accessToken));
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null && user.getType() == UserLoginType.KAKAO) {
            user.updateProfile(nickname, phoneNumber, address);
        }
    }
}