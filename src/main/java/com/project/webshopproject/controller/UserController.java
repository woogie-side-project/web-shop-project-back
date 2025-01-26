package com.project.webshopproject.controller;

import com.project.webshopproject.dto.ItemDto;
import com.project.webshopproject.dto.UserDto;
import com.project.webshopproject.repository.UserRepository;
import com.project.webshopproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user/mypage")
    public List<UserDto> getUserPage(){
        return userService.getUserPage();
    }
}
