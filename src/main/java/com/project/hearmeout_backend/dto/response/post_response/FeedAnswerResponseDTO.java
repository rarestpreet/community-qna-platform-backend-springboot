package com.project.hearmeout_backend.dto.response.post_response;

import com.project.hearmeout_backend.dto.response.comment_response.CommentResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class FeedAnswerResponseDTO {
    @Schema(description = "unique identifier of the answer post")
    private Long postId;

    @Schema(description = "indicates if the current user has voted on this answer")
    private boolean voted;

    @Schema(description = "unique identifier of the answer's author")
    private Long authorId;

    @Schema(description = "content of the answer")
    private String body;

    @Schema(description = "timestamp of when the answer was created")
    private String createdAt;

    @Schema(description = "status of the answer post")
    private String status;

    @Schema(description = "list of comments on the answer")
    private List<CommentResponseDTO> comments;

    @Schema(description = "total vote score of the answer")
    private int score;
}
