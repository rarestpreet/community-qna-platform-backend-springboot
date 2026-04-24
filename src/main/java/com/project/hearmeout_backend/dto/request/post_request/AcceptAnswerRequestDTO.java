package com.project.hearmeout_backend.dto.request.post_request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AcceptAnswerRequestDTO {
    private Long questionId;
    private Long answerId;
}
