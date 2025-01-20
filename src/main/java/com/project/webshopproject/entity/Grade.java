package com.project.webshopproject.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Grade {
    BASIC("BASIC"),
    PREMIUM("PREMIUM"),
    VIP("VIP"),
    ADMIN("ADMIN")
    ;

    private final String authority;

    public static boolean isAdmin(Grade grade) {
        return ADMIN.equals(grade);
    }
}