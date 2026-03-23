package com.project.hearmeout_backend.mapper;

import com.project.hearmeout_backend.dto.request.vote_request.VoteRequestDTO;
import com.project.hearmeout_backend.model.Post;
import com.project.hearmeout_backend.model.User;
import com.project.hearmeout_backend.model.Vote;

public class VoteMapper {

    public static Vote toVoteEntity(VoteRequestDTO voteRequestDTO, User user, Post post) {
        return Vote.builder()
                .user(user)
                .post(post)
                .voteType(voteRequestDTO.getVoteType())
                .build();
    }
}
