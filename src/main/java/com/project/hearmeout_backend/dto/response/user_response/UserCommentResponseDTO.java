package com.project.hearmeout_backend.dto.response.user_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class UserCommentResponseDTO {
    @Schema(description = "unique identifier of the comment")
    private Long id;

    @Schema(description = "timestamp of when the comment was created")
    private LocalDateTime createdAt;

    @Schema(description = "content of the comment")
    private String body;
}
