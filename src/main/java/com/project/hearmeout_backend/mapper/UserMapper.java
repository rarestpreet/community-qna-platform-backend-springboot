package com.project.hearmeout_backend.mapper;

import com.project.hearmeout_backend.dto.request.security_request.RegisterRequestDTO;
import com.project.hearmeout_backend.dto.response.user_response.HomeUserProfileResponseDTO;
import com.project.hearmeout_backend.dto.response.user_response.UserProfileResponseDTO;
import com.project.hearmeout_backend.model.User;
import org.jspecify.annotations.Nullable;

public class UserMapper {

    public static UserProfileResponseDTO toProfileDTO(User user) {
        return UserProfileResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .createdAt(user.getCreatedAt())
                .reputation(user.getReputation())
                .build();
    }

    public static User toProfileEntity(RegisterRequestDTO registerDTO, String encryptedPassword) {
        return User.builder()
                .username(registerDTO.getUsername())
                .password(encryptedPassword)
                .email(registerDTO.getEmail())
                .build();
    }

    public static @Nullable HomeUserProfileResponseDTO toHomeUserProfileResponseDTO(User user) {
        return HomeUserProfileResponseDTO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .isAccountVerified(user.isAccountVerified())
                .build();
    }
}
