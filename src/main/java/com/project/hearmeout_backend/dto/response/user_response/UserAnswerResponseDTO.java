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
public class UserAnswerResponseDTO {

    @Schema(description = "content of the answer")
    private String body;

    @Schema(description = "status of the answer")
    private PostStatus postStatus;

    @Schema(description = "total vote score of the answer")
    private int score;

    @Schema(description = "timestamp of when the answer was created")
    @JsonFormat(pattern = "dd-MM-yyy")
    private LocalDateTime updatedAt;

    @Schema(description = "postId of question associated with the answer (for navigation)")
    private Long navigationPostId;

    @Schema(description = "title of question associated with answer")
    private String parentPostTitle;
}
