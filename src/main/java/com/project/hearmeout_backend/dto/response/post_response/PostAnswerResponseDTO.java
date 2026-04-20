package com.project.hearmeout_backend.dto.response.post_response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.hearmeout_backend.dto.response.comment_response.CommentResponseDTO;
import com.project.hearmeout_backend.model.enums.PostStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostAnswerResponseDTO {
    @Schema(description = "unique identifier of the answer post")
    private Long postId;

    @Schema(description = "indicates if the current user has voted on this answer")
    private boolean voted;

    @Schema(description = "unique identifier of the answer's author")
    private String authorUsername;

    @Schema(description = "content of the answer")
    private String body;

    @Schema(description = "timestamp of when the answer was created")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime updatedAt;

    @Schema(description = "status of the answer post")
    private PostStatus postStatus;

    @Schema(description = "list of comments on the answer")
    private List<CommentResponseDTO> comments;

    @Schema(description = "total vote score of the answer")
    private int score;

    @Schema(description = "indicate if curr User can modify the answer")
    private boolean operable;

    public PostAnswerResponseDTO(Long postId, String authorUsername, String body, int score, LocalDateTime updatedAt, PostStatus postStatus) {
        this.postId = postId;
        this.authorUsername = authorUsername;
        this.body = body;
        this.score = score;
        this.updatedAt = updatedAt;
        this.postStatus = postStatus;
    }
}
