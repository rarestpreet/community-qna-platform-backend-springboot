package com.project.hearmeout_backend.dto.response.post_response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.hearmeout_backend.dto.response.tag_response.TagResponseDTO;
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
public class FeedQuestionResponseDTO {
    @Schema(description = "unique identifier of the post")
    private Long navigationPostId;

    @Schema(description = "unique identifier of the post's author")
    private String authorUsername;

    @Schema(description = "title of the post")
    private String title;

    @Schema(description = "total vote score of the post")
    private int score;

    @Schema(description = "timestamp of when the post was created")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime updatedAt;

    @Schema(description = "status of the post")
    private PostStatus postStatus;

    @Schema(description = "list of tags associated with the post")
    private List<TagResponseDTO> tags;

    public FeedQuestionResponseDTO(Long navigationPostId, String authorUsername, String title, int score, LocalDateTime updatedAt, PostStatus postStatus) {
        this.navigationPostId = navigationPostId;
        this.authorUsername = authorUsername;
        this.title = title;
        this.score = score;
        this.updatedAt = updatedAt;
        this.postStatus = postStatus;
    }
}
