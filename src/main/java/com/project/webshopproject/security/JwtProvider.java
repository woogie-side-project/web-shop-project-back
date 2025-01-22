package com.project.webshopproject.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "Jwt Provider")
public class JwtProvider {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESHTOKEN_HEADER = "RefreshToken";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final long TOKEN_TIME = 30 * 60 * 1000L; // 30분
    public static final long REFRESH_TOKEN_TIME = 14 * 24 * 60 * 60 * 1000L; // 2주
    private final UserDetailsServiceImpl userDetailsService;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    //TODO 시크릿 키 수정
//    @Value("${jwt.secret.key}")
    private String secretKey = "7JeQ67iM66as65Oc66eB7YGs7Iuc7YGs66a/7YKk7J6F64uI64uk7Iqk7YyM66W07YOA";
    private Key key;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createAccessToken(String email) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(email)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    public String createRefreshToken(String email) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(email)
                        .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        log.error("토큰을 찾을 수 없음 : {}", tokenValue);
        throw new IllegalArgumentException("토큰을 찾을 수 없습니다.");
    }

    public boolean validateToken(String token) {
        try {
//            if (isExistBlacklistToken(token)) {
//                log.error("블랙리스트 토큰입니다.");
//                throw new TokenException(TokenErrorCode.IS_BLACKLIST_TOKEN);
//            }
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            throw new IllegalArgumentException("유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
            throw new IllegalArgumentException("만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            throw new IllegalArgumentException("지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
            throw new IllegalArgumentException("잘못된 JWT 토큰 입니다.");
        }
    }

    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public Authentication getAuthentication(String token) {
        String email = extractEmail(token);
        UserDetails userDetails = userDetailsService.loadUserByEmail(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());
    }

    public String getTokenFromRequest(HttpServletRequest req) {
        String accessToken = req.getHeader(AUTHORIZATION_HEADER);
        return substringToken(accessToken);
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public long extractExpirationMillis(String token) {
        return extractAllClaims(token).getExpiration().getTime();
    }

    public long getRemainingValidityMillis(String token) {
        Date expiration = extractExpiration(token);
        Date now = new Date();
        return expiration.getTime() - now.getTime();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

}