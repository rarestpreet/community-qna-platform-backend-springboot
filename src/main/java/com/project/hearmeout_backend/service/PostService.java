package com.project.hearmeout_backend.service;

import com.project.hearmeout_backend.dto.request.post_request.AcceptAnswerRequestDTO;
import com.project.hearmeout_backend.dto.request.post_request.AnswerSubmitRequestDTO;
import com.project.hearmeout_backend.dto.request.post_request.QuestionSubmitRequestDTO;
import com.project.hearmeout_backend.dto.response.comment_response.CommentResponseDTO;
import com.project.hearmeout_backend.dto.response.post_response.PostAnswerResponseDTO;
import com.project.hearmeout_backend.dto.response.post_response.QuestionPostResponseDTO;
import com.project.hearmeout_backend.dto.response.tag_response.TagResponseDTO;
import com.project.hearmeout_backend.exception.InvalidOperationException;
import com.project.hearmeout_backend.exception.PostNotFoundException;
import com.project.hearmeout_backend.exception.TagNotFoundException;
import com.project.hearmeout_backend.exception.UserNotFoundException;
import com.project.hearmeout_backend.mapper.PostMapper;
import com.project.hearmeout_backend.model.Post;
import com.project.hearmeout_backend.model.Tag;
import com.project.hearmeout_backend.model.User;
import com.project.hearmeout_backend.model.Vote;
import com.project.hearmeout_backend.model.enums.PostStatus;
import com.project.hearmeout_backend.model.enums.PostType;
import com.project.hearmeout_backend.repository.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepo;
    private final TagRepository tagRepo;
    private final UserService userService;
    private final VoteRepository voteRepo;
    private final CommentRepository commentRepo;
    private final UserRepository userRepo;

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

        author.setReputation(author.getReputation() + 4);
        userRepo.save(author);

        Post newPost = PostMapper.questionToPostEntity(questionSubmitRequestDTO, author, tags);
        postRepo.save(newPost);
    }

    @Transactional
    public void postNewAnswer(Long postId, AnswerSubmitRequestDTO answerSubmitRequestDTO, Long userId)
            throws UserNotFoundException, PostNotFoundException {
        Post parent = checkAndGetPost(postId);

        if (Objects.equals(userId, parent.getAuthor().getId())) {
            throw new InvalidOperationException("Invalid action: you cannot answer your own questions.");
        }

        if (parent.getPostType().equals(PostType.ANSWER)) {
            throw new InvalidOperationException("Invalid action: you can only answer questions.");
        }

        User author = userService.checkAndGetUserByUserId(userId);
        author.setReputation(author.getReputation() + 6);
        userRepo.save(author);

        parent.setStatus(PostStatus.ANSWERED);
        Post newPost = PostMapper.answerToPostEntity(answerSubmitRequestDTO, parent, author);
        postRepo.save(newPost);
    }

    @Transactional(readOnly = true)
    public QuestionPostResponseDTO getQuestionPost(Long postId, Long currUserId, String currUsername)
            throws PostNotFoundException {
        QuestionPostResponseDTO currPostDTO = postRepo.findQuestionPostDetailsDTO(postId, PostType.QUESTION)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));

        Vote currUserVoteOnQuestion = voteRepo.findByPostIdAndUserId(postId, currUserId).orElse(null);

        List<PostAnswerResponseDTO> answers = postRepo.findAnswersDTOByQuestionId(postId, PostType.ANSWER);

        List<Long> answerIds = answers.stream().map(PostAnswerResponseDTO::getPostId).toList();
        List<CommentResponseDTO> allAnswerComments = answerIds.isEmpty() ? List.of() :
                answerIds.stream()
                        .map(commentRepo::findCommentsDTOByPostId)
                        .flatMap(List::stream)
                        .toList();

        answers.forEach(answer -> {
            Vote currUserVoteOnAnswer = voteRepo.findByPostIdAndUserId(answer.getPostId(), currUserId).orElse(null);
            answer.setVoted(currUserVoteOnAnswer != null);
            answer.setOperable(answer.getAuthorUsername().equals(currUsername));
            answer.setVoteType(currUserVoteOnAnswer != null ? currUserVoteOnAnswer.getVoteType() : null);

            List<CommentResponseDTO> answerComments = allAnswerComments.stream()
                    .filter(c -> c.getNavigationPostId().equals(answer.getPostId()))
                    .peek(c -> {
                        c.setOperable(c.getAuthorUsername().equals(currUsername));
                    })
                    .toList();
            answer.setComments(answerComments);
        });
        List<TagResponseDTO> tags = tagRepo.findTagsDTOByPostId(postId);

        List<CommentResponseDTO> comments = commentRepo.findCommentsDTOByPostId(postId);
        comments.forEach(c -> c.setOperable(c.getAuthorUsername().equals(currUsername)));

        currPostDTO.setVoted(currUserVoteOnQuestion != null);
        currPostDTO.setVoteType(currUserVoteOnQuestion != null ? currUserVoteOnQuestion.getVoteType() : null);
        currPostDTO.setAnswers(answers);
        currPostDTO.setTags(tags);
        currPostDTO.setComments(comments);
        currPostDTO.setOperable(currPostDTO.getAuthorUsername().equals(currUsername));

        return currPostDTO;
    }

    @Transactional
    public void acceptAnswer(@Valid AcceptAnswerRequestDTO acceptAnswerRequestDTO, Long currUserId){
        Post question = checkAndGetPost(acceptAnswerRequestDTO.getQuestionId());
        Post answer = checkAndGetPost(acceptAnswerRequestDTO.getAnswerId());
        User answerAuthor = userService.checkAndGetUserByUserId(answer.getAuthor().getId());
        User questionAuthor = userService.checkAndGetUserByUserId(question.getAuthor().getId());

        if(!Objects.equals(questionAuthor.getId(), currUserId)){
            throw new InvalidOperationException("You can only accept solution for self-posted questions.");
        }

        if (answer.getParent() == null || !Objects.equals(answer.getParent().getId(), question.getId())) {
            throw new InvalidOperationException("This answer does not belong to the specified question.");
        }

        if(Objects.equals(answer.getStatus(),PostStatus.ACCEPTED)){
            questionAuthor.setReputation(questionAuthor.getReputation() - 3);
            answerAuthor.setReputation(answerAuthor.getReputation() - 7);
            question.setStatus(PostStatus.ANSWERED);
            answer.setStatus(PostStatus.UNREVIEWED);
            answer.setScore(answer.getScore() - 5);

            postRepo.save(question);
            postRepo.save(answer);
            userRepo.save(answerAuthor);
            userRepo.save(questionAuthor);

            return;
        }

        if(question.getStatus() == PostStatus.CLOSED){
            throw new InvalidOperationException("Question already resolved.");
        }

        questionAuthor.setReputation(questionAuthor.getReputation() + 3);
        answerAuthor.setReputation(answerAuthor.getReputation() + 7);
        question.setStatus(PostStatus.CLOSED);
        answer.setStatus(PostStatus.ACCEPTED);
        answer.setScore(answer.getScore() + 5);

        postRepo.save(question);
        postRepo.save(answer);
        userRepo.save(answerAuthor);
        userRepo.save(questionAuthor);
    }
}
