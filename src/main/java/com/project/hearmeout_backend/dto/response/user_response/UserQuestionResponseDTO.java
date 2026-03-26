package com.project.hearmeout_backend.dto.response.user_response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class UserQuestionResponseDTO {
    private Long postId;
    private String title;
    private int score;
    private LocalDateTime createdAt;
    private String status;
}
