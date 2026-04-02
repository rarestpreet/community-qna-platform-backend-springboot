package com.project.hearmeout_backend.dto.response.user_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class UserQuestionResponseDTO {
    @Schema(description = "unique identifier of the question post")
    private Long postId;

    @Schema(description = "title of the question")
    private String title;

    @Schema(description = "total vote score of the question")
    private int score;

    @Schema(description = "timestamp of when the question was created")
    private LocalDateTime createdAt;

    @Schema(description = "status of the question")
    private String status;
}

