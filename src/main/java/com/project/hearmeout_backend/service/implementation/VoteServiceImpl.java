package com.project.hearmeout_backend.service.implementation;

import com.project.hearmeout_backend.dto.request.vote_request.VoteRequestDTO;
import com.project.hearmeout_backend.exception.InvalidOperationException;
import com.project.hearmeout_backend.mapper.VoteMapper;
import com.project.hearmeout_backend.model.Post;
import com.project.hearmeout_backend.model.User;
import com.project.hearmeout_backend.model.Vote;
import com.project.hearmeout_backend.model.enums.VoteType;
import com.project.hearmeout_backend.repository.PostRepository;
import com.project.hearmeout_backend.repository.UserRepository;
import com.project.hearmeout_backend.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl {

    private final VoteRepository voteRepo;
    private final UserServiceImpl userServiceImpl;
    private final PostServiceImpl postServiceImpl;
    private final UserRepository userRepo;
    private final PostRepository postRepo;

    @Transactional
    public void handleVote(VoteRequestDTO voteRequestDTO, Long userId) {
        Vote existingVote = voteRepo.findByPostIdAndUserId(
                voteRequestDTO.getPostId(),
                userId
        ).orElse(null);

        User currUser = userServiceImpl.checkAndGetUserByUserId(userId);
        Post post = postServiceImpl.checkAndGetPost(voteRequestDTO.getPostId());
        User author = userServiceImpl.checkAndGetUserByUserId(post.getAuthor().getId());
        VoteType newVoteType = voteRequestDTO.getVoteType();

        if (Objects.equals(author.getId(), currUser.getId())) {
            throw new InvalidOperationException("Invalid action: you cannot vote your own posts.");
        }

        // new vote, so either +1 or -1 to post, author, voter for upvote or downvote respectively
        if (existingVote == null) {
            if (newVoteType == VoteType.UPVOTE) {
                author.setReputation(author.getReputation() + 1);
                post.setScore(post.getScore() + 1);
            } else {
                author.setReputation(author.getReputation() - 1);
                post.setScore(post.getScore() - 1);
            }
            userRepo.save(author);
            postRepo.save(post);

            currUser.setReputation(currUser.getReputation() + 1);
            userRepo.save(currUser);

            Vote vote = VoteMapper.toVoteEntity(voteRequestDTO, currUser, post);
            voteRepo.save(vote);

            return;
        }

        VoteType oldVoteType = existingVote.getVoteType();

        // vote removed, so 1 point deduction or gain for upvote or downvote respectively
        if (oldVoteType == newVoteType) {
            if (newVoteType == VoteType.UPVOTE) {
                author.setReputation(author.getReputation() - 1);
                post.setScore(post.getScore() - 1);
            } else {
                author.setReputation(author.getReputation() + 1);
                post.setScore(post.getScore() + 1);
            }
            postRepo.save(post);
            userRepo.save(author);

            currUser.setReputation(currUser.getReputation() - 1);
            userRepo.save(currUser);

            voteRepo.removeVoteByPostIdAndUserId(post.getId(), userId);

        }
        // vote changed, so 2 point deduction or gain for downvote or upvote respectively
        else {
            if (newVoteType == VoteType.UPVOTE) {
                author.setReputation(author.getReputation() + 2);
                post.setScore(post.getScore() + 2);
            } else {
                author.setReputation(author.getReputation() - 2);
                post.setScore(post.getScore() - 2);
            }
            existingVote.setVoteType(newVoteType);
            voteRepo.save(existingVote);
            postRepo.save(post);
            userRepo.save(author);
        }
    }
}
