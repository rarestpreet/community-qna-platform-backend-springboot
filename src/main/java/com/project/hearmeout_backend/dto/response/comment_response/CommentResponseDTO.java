package com.project.hearmeout_backend.dto.response.comment_response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponseDTO {
    private Long commentId;
    private String body;
    private Long postId;
    private Long authorId;
    private boolean isEditable;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
