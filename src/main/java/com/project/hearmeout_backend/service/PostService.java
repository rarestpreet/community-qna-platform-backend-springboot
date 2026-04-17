package com.project.hearmeout_backend.service;

import com.project.hearmeout_backend.dto.request.post_request.AnswerSubmitRequestDTO;
import com.project.hearmeout_backend.dto.request.post_request.QuestionSubmitRequestDTO;
import com.project.hearmeout_backend.dto.response.post_response.QuestionPostResponseDTO;
import com.project.hearmeout_backend.dto.response.comment_response.CommentResponseDTO;
import com.project.hearmeout_backend.dto.response.post_response.FeedAnswerResponseDTO;
import com.project.hearmeout_backend.dto.response.tag_response.TagResponseDTO;
import com.project.hearmeout_backend.exception.InvalidOperationException;
import com.project.hearmeout_backend.exception.PostNotFoundException;
import com.project.hearmeout_backend.exception.TagNotFoundException;
import com.project.hearmeout_backend.exception.UserNotFoundException;
import com.project.hearmeout_backend.mapper.CommentMapper;
import com.project.hearmeout_backend.mapper.PostMapper;
import com.project.hearmeout_backend.mapper.TagMapper;
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
import java.util.Objects;

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

    @Transactional(readOnly = true)
    public QuestionPostResponseDTO getQuestionPost(Long postId, Long userId)
            throws PostNotFoundException {
        Post currPost = checkAndGetPost(postId);

        Long finalUserId = userId == null ? 0L : userId;
        boolean hasVoted = userId != null && voteRepo.existsByPostIdAndUserId(postId, userId);

        List<FeedAnswerResponseDTO> answers = currPost.getAnswers().stream()
                .map(answer -> {
                    boolean userVotedOnAnswer = answer.getVotes().stream()
                            .anyMatch(vote -> Objects.equals(vote.getUser().getId(), finalUserId));
                    List<CommentResponseDTO> answerComments = answer.getComments().stream()
                            .map(comment -> CommentMapper.toCommentResponseDTO(
                                    comment, finalUserId, comment.getAuthor().getId(), comment.getPost().getId()))
                            .toList();
                    return PostMapper.toFeedAnswerResponseDTO(answer, userVotedOnAnswer, answerComments);
                }).toList();

        List<TagResponseDTO> tags = currPost.getTags().stream()
                .map(TagMapper::toTagResponseDTO)
                .toList();

        List<CommentResponseDTO> comments = currPost.getComments().stream()
                .map(comment -> CommentMapper.toCommentResponseDTO(
                        comment, finalUserId, comment.getAuthor().getId(), comment.getPost().getId()))
                .toList();

        return PostMapper.toQuestionPostResponseDTO(currPost, hasVoted, answers, tags, comments);
    }
}
