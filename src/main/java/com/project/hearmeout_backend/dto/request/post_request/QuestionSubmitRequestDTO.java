package com.project.hearmeout_backend.dto.request.post_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionSubmitRequestDTO {

    @NotBlank(message = "Question title is required")
    @Size(min = 15, max = 150, message = "Question must be 15 to 150 characters long")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 50, max = 500, message = "Describe question in 50 to 500 characters")
    private String body;

    @NotNull
    private Long authorId;

    @NotEmpty(message = "At least one tag is required")
    @Size(min = 1, max = 10, message = "Must contain 1 to 10 tags")
    private List<Long> tagIds;
}
