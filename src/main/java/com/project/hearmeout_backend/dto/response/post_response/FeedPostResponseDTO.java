package com.project.hearmeout_backend.dto.response.post_response;

import com.project.hearmeout_backend.dto.response.tag_response.TagResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class FeedPostResponseDTO {
    @Schema(description = "unique identifier of the post")
    private Long postId;

    @Schema(description = "unique identifier of the post's author")
    private Long authorId;

    @Schema(description = "title of the post")
    private String title;

    @Schema(description = "total vote score of the post")
    private int score;

    @Schema(description = "timestamp of when the post was created")
    private LocalDateTime createdAt;

    @Schema(description = "status of the post")
    private String status;

    @Schema(description = "list of tags associated with the post")
    private List<TagResponseDTO> tags;
}
