package com.project.webshopproject.service;

import com.project.webshopproject.dto.UserSignupRequestDto;
import com.project.webshopproject.entity.Grade;
import com.project.webshopproject.entity.User;
import com.project.webshopproject.entity.UserLoginType;
import com.project.webshopproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j(topic = "User Service")
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(final UserSignupRequestDto requestDto) {
        User user = new User(
                requestDto.username(),
                requestDto.nickname(),
                requestDto.email(),
                passwordEncoder.encode(requestDto.password()),
                requestDto.phoneNumber(),
                requestDto.address(),
                Grade.BASIC,
                UserLoginType.COMMON
        );
        userRepository.save(user);
    }

}
