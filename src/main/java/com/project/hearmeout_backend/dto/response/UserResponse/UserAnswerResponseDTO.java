package com.project.hearmeout_backend.dto.response.UserResponse;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class UserAnswerResponseDTO {
    private Long id;
    private String body;
    private LocalDateTime date;
    private String status;
    private int score;
}
