package com.project.hearmeout_backend.dto.response.user_response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class UserAnswerResponseDTO {
    private Long postId;
    private String body;
    private String status;
    private int score;
    private LocalDateTime createdAt;
}
