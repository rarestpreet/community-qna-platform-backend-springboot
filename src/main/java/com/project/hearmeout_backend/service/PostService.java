package com.project.hearmeout_backend.service;

import com.project.hearmeout_backend.dto.request.post_request.AnswerSubmitRequestDTO;
import com.project.hearmeout_backend.dto.request.post_request.QuestionSubmitRequestDTO;
import com.project.hearmeout_backend.dto.response.post_response.QuestionPostResponseDTO;
import com.project.hearmeout_backend.exception.PostNotFoundException;
import com.project.hearmeout_backend.exception.TagNotFoundException;
import com.project.hearmeout_backend.exception.UserNotFoundException;
import com.project.hearmeout_backend.mapper.PostMapper;
import com.project.hearmeout_backend.model.CustomUserDetails;
import com.project.hearmeout_backend.model.Post;
import com.project.hearmeout_backend.model.Tag;
import com.project.hearmeout_backend.model.User;
import com.project.hearmeout_backend.model.enums.PostStatus;
import com.project.hearmeout_backend.repository.PostRepository;
import com.project.hearmeout_backend.repository.TagRepository;
import com.project.hearmeout_backend.repository.UserRepository;
import com.project.hearmeout_backend.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private final UserRepository userRepo;

    public Post checkAndGetPost(Long postId)
            throws PostNotFoundException {
        return postRepo.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));
    }

    @Transactional
    public void postNewQuestion(QuestionSubmitRequestDTO questionSubmitRequestDTO)
            throws UserNotFoundException, TagNotFoundException {
        User author = userService.checkAndGetUser(questionSubmitRequestDTO.getAuthorId());
        List<Tag> tags = tagRepo.findAllById(questionSubmitRequestDTO.getTagIds());

        if(tags.isEmpty() ||
                questionSubmitRequestDTO.getTagIds().size() != tags.size()) {
            throw new TagNotFoundException("Some tags do not exist");
        }

        Post newPost = PostMapper.questionToPostEntity(questionSubmitRequestDTO, author, tags);

        postRepo.save(newPost);
    }

    @Transactional
    public void postNewAnswer(Long postId, AnswerSubmitRequestDTO answerSubmitRequestDTO)
            throws UserNotFoundException, PostNotFoundException {
        Post parent = checkAndGetPost(postId);
        parent.setPostStatus(PostStatus.ANSWERED);

        User author = userService.checkAndGetUser(answerSubmitRequestDTO.getAuthorId());

        Post newPost = PostMapper.answerToPostEntity(answerSubmitRequestDTO, parent, author);
        postRepo.save(newPost);
    }

    @Transactional
    public QuestionPostResponseDTO getQuestionPost(Long postId, Authentication authentication)
            throws PostNotFoundException {
        Post currPost = checkAndGetPost(postId);

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {

            return PostMapper.toQuestionPostResponseDTO(currPost, false, 0L);
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof CustomUserDetails userDetails)) {
            return PostMapper.toQuestionPostResponseDTO(currPost, false, 0L);
        }

        User currUser = userRepo.findByEmail(userDetails.getUsername())
                .orElseThrow(() ->
                        new UserNotFoundException("User not found. Enter registered email")
                );
        Long currUserId = currUser.getId();
        boolean hasVoted = voteRepo.existsByPostIdAndUserId(postId, currUserId);

        return PostMapper.toQuestionPostResponseDTO(currPost, hasVoted, currUserId);
    }
}
