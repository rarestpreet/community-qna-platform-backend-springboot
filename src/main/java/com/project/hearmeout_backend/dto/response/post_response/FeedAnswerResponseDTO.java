package com.project.hearmeout_backend.dto.response.post_response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FeedAnswerResponseDTO {
    private Long postId;
    private boolean voted;
    private Long authorId;
    private String body;
    private LocalDateTime createdAt;
    private String status;
    private int score;
}
