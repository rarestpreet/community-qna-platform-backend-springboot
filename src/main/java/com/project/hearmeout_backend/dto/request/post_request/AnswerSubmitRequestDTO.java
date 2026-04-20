package com.project.hearmeout_backend.dto.request.post_request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnswerSubmitRequestDTO {

    @NotBlank(message = "Answer is required")
    @Schema(description = "post (answer) content")
    @Size(min = 20, max = 500, message = "Describe question in 20 to 500 characters")
    private String body;
}