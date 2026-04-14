package com.project.hearmeout_backend.dto.response.post_response;

import com.project.hearmeout_backend.dto.response.comment_response.CommentResponseDTO;
import com.project.hearmeout_backend.dto.response.tag_response.TagResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Getter
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
    private List<FeedAnswerResponseDTO> answers;

    @Schema(description = "unique identifier of the post's author")
    private Long authorId;

    @Schema(description = "list of tags associated with the post")
    private List<TagResponseDTO> tags;

    @Schema(description = "indicates if the current user has voted on this post")
    private boolean hasVoted;

    @Schema(description = "list of comments on the post")
    private List<CommentResponseDTO> comments;

    @Schema(description = "timestamp of when the post was created")
    private String createdAt;

    @Schema(description = "status of the post")
    private String postStatus;

    @Schema(description = "total vote score of the post")
    private int score;
}
