package com.project.hearmeout_backend.dto.request.vote_request;

import com.project.hearmeout_backend.model.enums.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class VoteRequestDTO {
    private Long voteId;
    private Long userId;
    private Long postId;
    private VoteType voteType;
}
