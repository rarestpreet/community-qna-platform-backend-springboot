package com.project.hearmeout_backend.dto.request.post_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class AnswerSubmitRequestDTO {

    @NotBlank(message = "Answer is required")
    @Size(min = 20, max = 500, message = "Describe question in 20 to 500 characters")
    private String body;

    @NotNull(message = "Post id is required")
    private Long parentPostId;
}