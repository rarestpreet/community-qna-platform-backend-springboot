package com.project.hearmeout_backend.dto.request.vote_request;

import com.project.hearmeout_backend.model.enums.VoteType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class VoteRequestDTO {
    private Long postId;
    private VoteType voteType;
}
