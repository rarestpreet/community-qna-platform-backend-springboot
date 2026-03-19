package com.project.hearmeout_backend.dto.response.UserResponse;

import com.project.hearmeout_backend.model.Tag;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserQuestionResponseDTO {
    private Long id;
    private String title;
    private String body;
    private int score;
    private LocalDateTime date;
    private String status;
    private List<Tag> tags;
}
