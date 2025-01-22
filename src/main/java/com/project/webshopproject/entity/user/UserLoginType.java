package com.project.webshopproject.entity.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserLoginType {
    KAKAO("KAKAO"),
    NAVER("NAVER"),
    COMMON("COMMON")
    ;

    private final String value;
}
