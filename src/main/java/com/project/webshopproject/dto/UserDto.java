package com.project.webshopproject.dto;

import com.project.webshopproject.entity.Grade;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {
    private String userName;
    private String email;
    private String nickName;
    private String phoneNumber;
    private String address;
    private Grade grade;
}
