package com.project.hearmeout_backend.dto.response.post_response;

import com.project.hearmeout_backend.dto.response.comment_response.CommentResponseDTO;
import com.project.hearmeout_backend.dto.response.tag_response.TagResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class QuestionPostResponseDTO {
    private Long postId;
    private String title;
    private String body;
    private List<FeedAnswerResponseDTO> answers;
    private Long authorId;
    private List<TagResponseDTO> tags;
    private boolean hasVoted;
    private List<CommentResponseDTO> comments;
    private LocalDateTime createdAt;
    private String postStatus;
    private int score;
}
