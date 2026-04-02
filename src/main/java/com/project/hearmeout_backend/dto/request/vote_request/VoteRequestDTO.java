package com.project.hearmeout_backend.dto.request.vote_request;

import com.project.hearmeout_backend.model.enums.VoteType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VoteRequestDTO {

    @NotNull(message = "postId cannot be null")
    @Schema(description = "post associated with the vote")
    private Long postId;

    @NotNull(message = "define the type of vote")
    @Schema(description = "define the vote type (upvote/downvote)")
    private VoteType voteType;
}
