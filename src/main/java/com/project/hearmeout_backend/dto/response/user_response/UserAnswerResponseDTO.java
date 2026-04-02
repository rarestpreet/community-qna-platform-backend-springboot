package com.project.hearmeout_backend.dto.response.user_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class UserAnswerResponseDTO {
    @Schema(description = "unique identifier of the answer post")
    private Long postId;

    @Schema(description = "content of the answer")
    private String body;

    @Schema(description = "status of the answer")
    private String status;

    @Schema(description = "total vote score of the answer")
    private int score;

    @Schema(description = "timestamp of when the answer was created")
    private LocalDateTime createdAt;
}
