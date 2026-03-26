package com.project.hearmeout_backend.dto.response.user_response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class UserProfileResponseDTO {
    private Long id;
    private String username;
    private int reputation;
    private LocalDateTime createdAt;
}
