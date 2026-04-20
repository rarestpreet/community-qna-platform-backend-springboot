package com.project.hearmeout_backend.dto.response.user_response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class UserCommentResponseDTO {

    @Schema(description = "content of the comment")
    private String body;

    @Schema(description = "unique identifier of the associated post (for navigation)")
    private Long navigationPostId;

    @Schema(description = "glimpse of content of associated post")
    private String postContent;

    @Schema(description = "timestamp of the last update")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime updatedAt;
}
