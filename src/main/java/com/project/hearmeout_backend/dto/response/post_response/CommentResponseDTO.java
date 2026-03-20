package com.project.hearmeout_backend.dto.response.post_response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponseDTO {
    private Long id;
    private String body;
    private String authorUsername;
    private LocalDateTime createdAt;
}
