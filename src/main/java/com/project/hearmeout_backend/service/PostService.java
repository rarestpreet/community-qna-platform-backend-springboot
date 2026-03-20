package com.project.hearmeout_backend.service;

import com.project.hearmeout_backend.dto.request.post_request.AnswerSubmitRequestDTO;
import com.project.hearmeout_backend.dto.request.post_request.QuestionSubmitRequestDTO;
import com.project.hearmeout_backend.dto.response.post_response.QuestionPostResponseDTO;
import com.project.hearmeout_backend.exception.PostNotFoundException;
import com.project.hearmeout_backend.exception.UserNotFoundException;
import com.project.hearmeout_backend.mapper.PostMapper;
import com.project.hearmeout_backend.model.Post;
import com.project.hearmeout_backend.model.Tag;
import com.project.hearmeout_backend.model.User;
import com.project.hearmeout_backend.repository.PostRepository;
import com.project.hearmeout_backend.repository.TagRepository;
import com.project.hearmeout_backend.repository.VoteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public void postNewQuestion(QuestionSubmitRequestDTO questionSubmitRequestDTO)
            throws UserNotFoundException {
        Post newPost = PostMapper.questionToPostEntity(questionSubmitRequestDTO);
        User author = userService.checkAndGetUser(questionSubmitRequestDTO.getAuthorId());

        // Resolve tags from the database using the provided IDs
        List<Tag> tags = tagRepo.findAllById(questionSubmitRequestDTO.getTagIds());
        newPost.setTags(tags);
        newPost.setAuthor(author);

        postRepo.save(newPost);
    }

    @Transactional
    public void postNewAnswer(Long postId, AnswerSubmitRequestDTO answerSubmitRequestDTO)
            throws UserNotFoundException, PostNotFoundException {
        Post newPost = PostMapper.answerToPostEntity(answerSubmitRequestDTO);

        Post parent = checkAndGetPost(postId);
        User author = userService.checkAndGetUser(answerSubmitRequestDTO.getAuthorId());

        newPost.setAuthor(author);
        newPost.setParent(parent);

        postRepo.save(newPost);
    }

    @Transactional
    public QuestionPostResponseDTO getQuestionPost(Long postId, Long currUserId)
            throws PostNotFoundException {
        Post currPost = checkAndGetPost(postId);
        boolean hasVoted = voteRepo.existsByPostIdAndUserId(postId, currUserId);

        return PostMapper.toQuestionPostResponseDTO(currPost, hasVoted, currUserId);
    }
}
