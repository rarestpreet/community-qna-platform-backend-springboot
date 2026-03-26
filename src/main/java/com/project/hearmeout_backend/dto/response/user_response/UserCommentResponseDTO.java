package com.project.hearmeout_backend.dto.response.user_response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserCommentResponseDTO {
    private Long id;
    private LocalDateTime createdAt;
    private String body;
}
