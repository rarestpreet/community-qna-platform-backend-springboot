package com.project.hearmeout_backend.dto.response.user_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class UserAnswerResponseDTO {
    private Long postId;
    private String body;
    private String status;
    private int score;
    private LocalDateTime createdAt;
}
