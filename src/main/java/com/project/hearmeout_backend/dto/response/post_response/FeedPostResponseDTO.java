package com.project.hearmeout_backend.dto.response.post_response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class FeedPostResponseDTO {
    private Long postId;
    private String authorUsername;
    private Long authorId;
    private String title;
    private int score;
    private LocalDateTime createdAt;
    private String status;
}
