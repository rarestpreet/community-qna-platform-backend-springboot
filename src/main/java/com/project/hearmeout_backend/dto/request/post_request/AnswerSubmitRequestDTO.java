package com.project.hearmeout_backend.dto.request.post_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerSubmitRequestDTO {

    @NotBlank(message = "Answer is required")
    @Size(min = 20, max = 500, message = "Describe question in 20 to 500 characters")
    private String body;

    @NotNull(message = "User id is required")
    private Long authorId;

    @NotNull(message = "Post id is required")
    private Long parentPostId;
}