package com.project.webshopproject.entity.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public record UserSignupRequestDto(
        @Length(max = 30, message = "이메일은 최대 30자까지 입력 가능합니다.")
        @Email(message = "유효한 이메일 형식이 아닙니다.")
        @NotBlank(message = "이메일은 필수 항목입니다.")
        String email,

        @Size(min = 2, max = 10, message = "사용자 이름은 2자 이상 10자 이하로 입력해야 합니다.")
        @NotBlank(message = "사용자 이름은 필수 항목입니다.")
        String username,

        @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하로 입력해야 합니다.")
        @NotBlank(message = "닉네임은 필수 항목입니다.")
        String nickname,

        @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "유효한 전화번호 형식이 아닙니다.")
        @NotBlank(message = "전화번호는 필수 항목입니다.")
        String phoneNumber,

        @Size(max = 20, message = "비밀번호는 20자 이하로 입력해야 합니다.")
        @NotBlank(message = "비밀번호는 필수 항목입니다.")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*]).{1,20}$",
                message = "비밀번호는 숫자와 특수문자를 하나 이상 포함해야 합니다."
        )
        String password,

        @NotNull(message = "주소는 필수 항목입니다.")
        @Size(max = 50, message = "주소는 50자 이하로 입력해야 합니다.")
        String address,

        @Size(max = 300, message = "Token은 300자 내로 입력해야 합니다.")
        String token
) {

}