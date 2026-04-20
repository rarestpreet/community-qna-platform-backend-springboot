package com.project.hearmeout_backend.mapper;

import com.project.hearmeout_backend.dto.request.security_request.RegisterRequestDTO;
import com.project.hearmeout_backend.model.User;

public class UserMapper {

    public static User toProfileEntity(RegisterRequestDTO registerDTO, String encryptedPassword) {
        return User.builder()
                .username(registerDTO.getUsername())
                .password(encryptedPassword)
                .email(registerDTO.getEmail())
                .build();
    }
}
