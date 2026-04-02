package com.project.hearmeout_backend.dto.response.comment_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class CommentResponseDTO {
    @Schema(description = "unique identifier of the comment")
    private Long commentId;

    @Schema(description = "content of the comment")
    private String body;

    @Schema(description = "unique identifier of the comment's author")
    private Long authorId;

    @Schema(description = "unique identifier of the associated post")
    private Long postId;

    @Schema(description = "timestamp of the last update")
    private LocalDateTime updatedAt;

    @Schema(description = "indicates if the comment can be edited by the current user")
    private boolean isEditable;
}
