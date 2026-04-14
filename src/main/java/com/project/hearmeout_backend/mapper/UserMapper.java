package com.project.hearmeout_backend.mapper;

import com.project.hearmeout_backend.dto.request.security_request.RegisterRequestDTO;
import com.project.hearmeout_backend.dto.response.user_response.HomeUserProfileResponseDTO;
import com.project.hearmeout_backend.dto.response.user_response.UserProfileResponseDTO;
import com.project.hearmeout_backend.model.User;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class UserMapper {

    public static UserProfileResponseDTO toProfileDTO(User user, Long userId) {
        return UserProfileResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .reputation(user.getReputation())
                .isOperable(Objects.equals(user.getId(), userId))
                .isAccountVerified(user.isAccountVerified())
                .build();
    }

    public static User toProfileEntity(RegisterRequestDTO registerDTO, String encryptedPassword) {
        return User.builder()
                .username(registerDTO.getUsername())
                .password(encryptedPassword)
                .email(registerDTO.getEmail())
                .build();
    }

    public static HomeUserProfileResponseDTO toHomeUserProfileResponseDTO(User user) {
        return HomeUserProfileResponseDTO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .isAccountVerified(user.isAccountVerified())
                .build();
    }
}
