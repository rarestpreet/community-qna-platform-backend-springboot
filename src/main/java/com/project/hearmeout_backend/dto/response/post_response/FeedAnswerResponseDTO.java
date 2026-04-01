package com.project.hearmeout_backend.dto.response.post_response;

import com.project.hearmeout_backend.dto.response.comment_response.CommentResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class FeedAnswerResponseDTO {
    private Long postId;
    private boolean voted;
    private Long authorId;
    private String body;
    private LocalDateTime createdAt;
    private String status;
    private List<CommentResponseDTO> comments;
    private int score;
}
