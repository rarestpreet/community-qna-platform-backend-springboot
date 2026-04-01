package com.project.hearmeout_backend.dto.response.post_response;

import com.project.hearmeout_backend.dto.response.tag_response.TagResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class FeedPostResponseDTO {
    private Long postId;
    private Long authorId;
    private String title;
    private int score;
    private LocalDateTime createdAt;
    private String status;
    private List<TagResponseDTO> tags;
}
