package com.project.webshopproject.controller;

import com.project.webshopproject.entity.user.dto.UserChangePasswordRequestDto;
import com.project.webshopproject.global.RestApiResponseDto;
import com.project.webshopproject.entity.user.dto.UserSignupRequestDto;
import com.project.webshopproject.security.UserDetailsImpl;
import com.project.webshopproject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    /**
     * 회원가입 기능
     *
     * @param requestDto: email, username, nickname, phone_number, password, token
     */
    @PostMapping("/user/signup")
    public ResponseEntity<RestApiResponseDto<String>> signup(
            @Valid @RequestBody final UserSignupRequestDto requestDto
    ) {
        userService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RestApiResponseDto.of("회원가입에 성공하였습니다!"));
    }

    /**
     * 비밀번호 변경 기능
     * @param requestDto: password, newPassword, confirmNewPassword
     * @param userDetails
     */
    @PatchMapping("/user/password")
    public ResponseEntity<RestApiResponseDto<String>> changePassword(
            @Valid @RequestBody final UserChangePasswordRequestDto requestDto,
            @AuthenticationPrincipal final UserDetailsImpl userDetails
    ) {
        try {
            userService.changePassword(requestDto,userDetails.getEmail());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(RestApiResponseDto.of("비밀번호가 변경되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(RestApiResponseDto.of(e.getMessage()));
        }
    }

}
