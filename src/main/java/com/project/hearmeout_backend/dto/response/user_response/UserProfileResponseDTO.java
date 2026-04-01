package com.project.hearmeout_backend.dto.response.user_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class UserProfileResponseDTO {
    private Long id;
    private String username;
    private int reputation;
    private LocalDateTime createdAt;
    private boolean isOperable;
}
