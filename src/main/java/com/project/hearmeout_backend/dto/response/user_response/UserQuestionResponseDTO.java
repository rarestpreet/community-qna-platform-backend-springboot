package com.project.hearmeout_backend.dto.response.user_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class UserQuestionResponseDTO {
    private Long postId;
    private String title;
    private int score;
    private LocalDateTime createdAt;
    private String status;
}

