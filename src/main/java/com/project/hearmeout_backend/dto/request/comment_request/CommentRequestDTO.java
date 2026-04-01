package com.project.hearmeout_backend.dto.request.comment_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequestDTO {

    @NotBlank(message = "Comment body is required")
    @Size(min = 10, max = 100, message = "Comment must be between 10 and 100 characters")
    private String body;

    @NotNull(message = "Post id is required")
    private Long postId;
}
