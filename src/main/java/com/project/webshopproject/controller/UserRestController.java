package com.project.webshopproject.controller;

import com.project.webshopproject.dto.RestApiResponseDto;
import com.project.webshopproject.dto.UserSignupRequestDto;
import com.project.webshopproject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    /**
     * 회원가입 기능
     *
     * @param requestDto : email, username, nickname, phone_number, password, token
     */
    @PostMapping("/users/signup")
    public ResponseEntity<RestApiResponseDto<String>> signup(
            @Valid @RequestBody final UserSignupRequestDto requestDto
    ) {
        userService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RestApiResponseDto.of("회원가입에 성공하였습니다!"));
    }

}
