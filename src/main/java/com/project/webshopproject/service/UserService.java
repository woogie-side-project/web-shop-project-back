package com.project.webshopproject.service;

import com.project.webshopproject.dto.ItemDto;
import com.project.webshopproject.dto.UserDto;
import com.project.webshopproject.entity.Items;
import com.project.webshopproject.entity.User;
import com.project.webshopproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserDto> getUserPage() {
        List<User> userInfo = userRepository.findAll();
        return userInfo.stream()
                .map(user -> new UserDto(
                        user.getUsername(),
                        user.getEmail(),
                        user.getNickname(),
                        user.getPhoneNumber(),
                        user.getAddress(),
                        user.getGrade()
                ))
                .collect(Collectors.toList());
    }
}
