package com.project.hearmeout_backend.dto.request.comment_request;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "comment context/body")
    private String body;

    @NotNull(message = "Post id is required")
    @Schema(description = "post associated with comment")
    private Long postId;
}
