package com.project.hearmeout_backend.dto.request.post_request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
public class AcceptAnswerRequestDTO {
    @Schema(description = "The unique identifier of the question")
    private Long questionId;

    @Schema(description = "The unique identifier of the answer being accepted")
    private Long answerId;
}
