package com.project.webshopproject.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserChangePasswordRequestDto(

        @NotBlank(message = "현재 비밀번호는 필수 항목입니다.")
        String password,

        @Size(max = 20, message = "새 비밀번호는 20자 이하로 입력해야 합니다.")
        @NotBlank(message = "새 비밀번호는 필수 항목입니다.")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*]).{1,20}$",
                message = "새 비밀번호는 숫자와 특수문자를 하나 이상 포함해야 합니다."
        )
        String newPassword,

        @Size(max = 20, message = "새 비밀번호 확인은 20자 이하로 입력해야 합니다.")
        @NotBlank(message = "새 비밀번호 확인은 필수 항목입니다.")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*]).{1,20}$",
                message = "새 비밀번호 확인도 숫자와 특수문자를 하나 이상 포함해야 합니다."
        )
        String confirmNewPassword
) {
}
