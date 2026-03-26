package com.project.hearmeout_backend.dto.response.comment_response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponseDTO {
    private Long commentId;
    private String body;
    private Long authorId;
    private Long postId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isEditable;
}
