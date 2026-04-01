package com.project.hearmeout_backend.service;

import com.project.hearmeout_backend.dto.request.post_request.AnswerSubmitRequestDTO;
import com.project.hearmeout_backend.dto.request.post_request.QuestionSubmitRequestDTO;
import com.project.hearmeout_backend.dto.response.post_response.QuestionPostResponseDTO;
import com.project.hearmeout_backend.exception.InvalidOperationException;
import com.project.hearmeout_backend.exception.PostNotFoundException;
import com.project.hearmeout_backend.exception.TagNotFoundException;
import com.project.hearmeout_backend.exception.UserNotFoundException;
import com.project.hearmeout_backend.mapper.PostMapper;
import com.project.hearmeout_backend.model.Post;
import com.project.hearmeout_backend.model.Tag;
import com.project.hearmeout_backend.model.User;
import com.project.hearmeout_backend.model.enums.PostStatus;
import com.project.hearmeout_backend.model.enums.PostType;
import com.project.hearmeout_backend.repository.PostRepository;
import com.project.hearmeout_backend.repository.TagRepository;
import com.project.hearmeout_backend.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepo;
    private final TagRepository tagRepo;
    private final UserService userService;
    private final VoteRepository voteRepo;

    public Post checkAndGetPost(Long postId)
            throws PostNotFoundException {
        return postRepo.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));
    }

    @Transactional
    public void postNewQuestion(QuestionSubmitRequestDTO questionSubmitRequestDTO, Long userId)
            throws UserNotFoundException, TagNotFoundException {

        User author = userService.checkAndGetUserByUserId(userId);
        List<Tag> tags = tagRepo.findAllById(questionSubmitRequestDTO.getTagIds());

        if (questionSubmitRequestDTO.getTagIds().size() != tags.size()) {
            throw new TagNotFoundException("Some tags do not exist");
        }

        Post newPost = PostMapper.questionToPostEntity(questionSubmitRequestDTO, author, tags);

        postRepo.save(newPost);
    }

    @Transactional
    public void postNewAnswer(Long postId, AnswerSubmitRequestDTO answerSubmitRequestDTO, Long userId)
            throws UserNotFoundException, PostNotFoundException {
        Post parent = checkAndGetPost(postId);
        if(parent.getPostType().equals(PostType.ANSWER)){
            throw new InvalidOperationException("Invalid action: you can only answer questions.");
        }
        parent.setPostStatus(PostStatus.ANSWERED);

        User author = userService.checkAndGetUserByUserId(userId);

        Post newPost = PostMapper.answerToPostEntity(answerSubmitRequestDTO, parent, author);
        postRepo.save(newPost);
    }

    @Transactional
    public QuestionPostResponseDTO getQuestionPost(Long postId, Long userId)
            throws PostNotFoundException {
        Post currPost = checkAndGetPost(postId);

        // if user not logged in
        if (userId == null) {
            return PostMapper.toQuestionPostResponseDTO(currPost, false, 0L);
        }

        boolean hasVoted = voteRepo.existsByPostIdAndUserId(postId, userId);

        return PostMapper.toQuestionPostResponseDTO(currPost, hasVoted, userId);
    }
}
