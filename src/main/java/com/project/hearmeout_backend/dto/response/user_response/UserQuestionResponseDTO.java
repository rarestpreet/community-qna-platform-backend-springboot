package com.project.hearmeout_backend.dto.response.user_response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.hearmeout_backend.model.enums.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class UserQuestionResponseDTO {
    @Schema(description = "unique identifier of the question post to navigate to")
    private Long navigationPostId;

    @Schema(description = "title of the question")
    private String title;

    @Schema(description = "total vote score of the question")
    private int score;

    @Schema(description = "timestamp of when the question was modified (or created)")
    @JsonFormat(pattern = "dd-MM-yyy")
    private LocalDateTime updatedAt;

    @Schema(description = "status of the question")
    private PostStatus postStatus;
}

