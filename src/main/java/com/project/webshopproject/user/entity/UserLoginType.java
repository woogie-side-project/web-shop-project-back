package com.project.webshopproject.user.entity;

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
