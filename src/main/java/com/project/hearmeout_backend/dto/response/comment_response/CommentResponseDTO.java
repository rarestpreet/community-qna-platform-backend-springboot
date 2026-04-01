package com.project.hearmeout_backend.dto.response.comment_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class CommentResponseDTO {
    private Long commentId;
    private String body;
    private Long authorId;
    private Long postId;
    private LocalDateTime updatedAt;
    private boolean isEditable;
}
