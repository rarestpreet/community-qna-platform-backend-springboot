package com.project.hearmeout_backend.dto.response.comment_response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CommentResponseDTO {
    @Schema(description = "unique identifier of the comment")
    private Long commentId;

    @Schema(description = "content of the comment")
    private String body;

    @Schema(description = "unique identifier of the comment's author")
    private String authorUsername;

    @Schema(description = "unique identifier of the associated post")
    private Long navigationPostId;

    @Schema(description = "timestamp of the last update")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime updatedAt;

    @Schema(description = "indicates if the comment can be edited by the current user")
    private boolean operable;

    public CommentResponseDTO(Long commentId, String body, String authorUsername, Long navigationPostId, LocalDateTime updatedAt) {
        this.commentId = commentId;
        this.body = body;
        this.authorUsername = authorUsername;
        this.navigationPostId = navigationPostId;
        this.updatedAt = updatedAt;
    }
}
