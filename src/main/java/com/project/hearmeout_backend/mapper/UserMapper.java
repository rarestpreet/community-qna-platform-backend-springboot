package com.project.hearmeout_backend.mapper;

import com.project.hearmeout_backend.dto.request.SecurityRequest.RegisterRequestDTO;
import com.project.hearmeout_backend.dto.response.UserResponse.UserProfileResponseDTO;
import com.project.hearmeout_backend.model.User;

public class UserMapper {

    public static UserProfileResponseDTO toProfileDTO(User user) {
        return UserProfileResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .createdAt(user.getCreatedAt())
                .reputation(user.getReputation())
                .build();
    }

    public static User toProfileEntity(RegisterRequestDTO registerDTO) {
        return User.builder()
                .username(registerDTO.getUsername())
                .password(registerDTO.getPassword())
                .email(registerDTO.getEmail())
                .build();
    }

}
