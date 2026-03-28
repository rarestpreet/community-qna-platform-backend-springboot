package com.project.hearmeout_backend.service;

import com.project.hearmeout_backend.dto.request.vote_request.VoteRequestDTO;
import com.project.hearmeout_backend.mapper.VoteMapper;
import com.project.hearmeout_backend.model.Post;
import com.project.hearmeout_backend.model.User;
import com.project.hearmeout_backend.model.Vote;
import com.project.hearmeout_backend.model.enums.VoteType;
import com.project.hearmeout_backend.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepo;
    private final UserService userService;
    private final PostService postService;

    @Transactional
    public void handleVote(VoteRequestDTO voteRequestDTO) {
        Vote existingVote = voteRepo.findByPostIdAndUserId(
                voteRequestDTO.getPostId(),
                voteRequestDTO.getUserId()
        ).orElse(null);

        User user = userService.checkAndGetUserByUserId(voteRequestDTO.getUserId());
        Post post = postService.checkAndGetPost(voteRequestDTO.getPostId());
        VoteType newVoteType = voteRequestDTO.getVoteType();

        if (existingVote == null) {
            post.setScore(post.getScore() + (newVoteType == VoteType.UPVOTE ? 1 : -1));
            Vote vote = VoteMapper.toVoteEntity(voteRequestDTO, user, post);
            voteRepo.save(vote);
            return;
        }

        VoteType oldVoteType = existingVote.getVoteType();

        if (oldVoteType == newVoteType) {
            post.setScore(post.getScore() + (oldVoteType == VoteType.UPVOTE ? -1 : 1));
            voteRepo.removeVoteByPostIdAndUserId(post.getId(), user.getId());
        } else {
            post.setScore(post.getScore() + (newVoteType == VoteType.UPVOTE ? 2 : -2));
            existingVote.setVoteType(newVoteType);
        }
    }
}
