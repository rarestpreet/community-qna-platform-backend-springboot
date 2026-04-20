package com.project.hearmeout_backend.dto.response.post_response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.hearmeout_backend.dto.response.comment_response.CommentResponseDTO;
import com.project.hearmeout_backend.dto.response.tag_response.TagResponseDTO;
import com.project.hearmeout_backend.model.enums.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class QuestionPostResponseDTO {
    @Schema(description = "unique identifier of the post")
    private Long postId;

    @Schema(description = "title of the post")
    private String title;

    @Schema(description = "content of the post")
    private String body;

    @Schema(description = "list of answers to the question")
    private List<PostAnswerResponseDTO> answers;

    @Schema(description = "unique identifier of the post's author")
    private String authorUsername;

    @Schema(description = "list of tags associated with the post")
    private List<TagResponseDTO> tags;

    @Schema(description = "indicates if the current user has voted on this post")
    private boolean hasVoted;

    @Schema(description = "list of comments on the post")
    private List<CommentResponseDTO> comments;

    @Schema(description = "timestamp of when the post was created")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime updatedAt;

    @Schema(description = "status of the post")
    private PostStatus postStatus;

    @Schema(description = "total vote score of the post")
    private int score;

    @Schema(description = "indicate if curr User can modify the answer")
    private boolean operable;

    public QuestionPostResponseDTO(Long postId, String authorUsername, String title, String body, int score, LocalDateTime updatedAt, PostStatus postStatus) {
        this.postId = postId;
        this.authorUsername = authorUsername;
        this.title = title;
        this.body = body;
        this.score = score;
        this.updatedAt = updatedAt;
        this.postStatus = postStatus;
    }
}
