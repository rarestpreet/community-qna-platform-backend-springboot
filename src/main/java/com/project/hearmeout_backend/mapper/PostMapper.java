package com.project.hearmeout_backend.mapper;

import com.project.hearmeout_backend.dto.request.post_request.AnswerSubmitRequestDTO;
import com.project.hearmeout_backend.dto.request.post_request.QuestionSubmitRequestDTO;
import com.project.hearmeout_backend.dto.response.comment_response.CommentResponseDTO;
import com.project.hearmeout_backend.dto.response.post_response.FeedAnswerResponseDTO;
import com.project.hearmeout_backend.dto.response.post_response.FeedPostResponseDTO;
import com.project.hearmeout_backend.dto.response.post_response.QuestionPostResponseDTO;
import com.project.hearmeout_backend.dto.response.tag_response.TagResponseDTO;
import com.project.hearmeout_backend.dto.response.user_response.UserAnswerResponseDTO;
import com.project.hearmeout_backend.dto.response.user_response.UserQuestionResponseDTO;
import com.project.hearmeout_backend.model.Post;
import com.project.hearmeout_backend.model.Tag;
import com.project.hearmeout_backend.model.User;
import com.project.hearmeout_backend.model.enums.PostStatus;
import com.project.hearmeout_backend.model.enums.PostType;

import java.util.List;
import java.util.Objects;

import static com.project.hearmeout_backend.mapper.CommentMapper.toCommentResponseDTO;

public class PostMapper {

    public static Post answerToPostEntity(AnswerSubmitRequestDTO answerSubmitRequestDTO, Post parent, User author) {
        return Post.builder()
                .title(null)
                .body(answerSubmitRequestDTO.getBody())
                .postType(PostType.ANSWER)
                .parent(parent)
                .author(author)
                .postStatus(PostStatus.UNREVIEWED)
                .build();
    }

    public static Post questionToPostEntity(QuestionSubmitRequestDTO questionSubmitRequestDTO, User author, List<Tag> tags) {
        return Post.builder()
                .title(questionSubmitRequestDTO.getTitle())
                .body(questionSubmitRequestDTO.getBody())
                .postType(PostType.QUESTION)
                .author(author)
                .tags(tags)
                .body(questionSubmitRequestDTO.getBody())
                .postStatus(PostStatus.UNANSWERED)
                .build();
    }

    public static UserAnswerResponseDTO toUserAnswerResponseDTO(Post answer) {
        return UserAnswerResponseDTO.builder()
                .postId(answer.getId())
                .body(answer.getBody())
                .status(answer.getPostStatus().toString())
                .score(answer.getScore())
                .createdAt(answer.getCreatedAt())
                .build();
    }

    public static UserQuestionResponseDTO toUserQuestionResponseDTO(Post question) {
        return UserQuestionResponseDTO.builder()
                .postId(question.getId())
                .title(question.getTitle())
                .score(question.getScore())
                .createdAt(question.getCreatedAt())
                .status(question.getPostStatus().toString())
                .build();
    }

    public static FeedPostResponseDTO toFeedPostResponseDTO(Post question) {
        List<TagResponseDTO> tags = question.getTags().stream()
                .map(TagMapper::toTagResponseDTO)
                .toList();

        return FeedPostResponseDTO.builder()
                .postId(question.getId())
                .authorId(question.getAuthor().getId())
                .title(question.getTitle())
                .score(question.getScore())
                .createdAt(question.getCreatedAt())
                .status(question.getPostStatus().toString())
                .tags(tags)
                .build();
    }

    public static FeedAnswerResponseDTO toFeedAnswerResponseDTO(Post answer, boolean hasVoted) {
        return FeedAnswerResponseDTO.builder()
                .body(answer.getBody())
                .postId(answer.getId())
                .authorId(answer.getAuthor().getId())
                .voted(hasVoted)
                .score(answer.getScore())
                .createdAt(answer.getCreatedAt())
                .status(answer.getPostStatus().toString())
                .build();
    }

    public static QuestionPostResponseDTO toQuestionPostResponseDTO(Post question, boolean hasVoted, Long currUserId) {
        List<FeedAnswerResponseDTO> answers = question.getAnswers().stream()
                .map(answer -> {
                    boolean userVotedOnAnswer = answer.getVotes().stream()
                            .anyMatch(vote -> Objects.equals(vote.getUser().getId(), currUserId));
                    return PostMapper.toFeedAnswerResponseDTO(answer, userVotedOnAnswer);
                }).toList();

        List<TagResponseDTO> tags = question.getTags().stream()
                .map(TagMapper::toTagResponseDTO)
                .toList();

        List<CommentResponseDTO> comments = question.getComments().stream()
                .map(comment -> toCommentResponseDTO(comment, currUserId))
                .toList();

        return QuestionPostResponseDTO.builder()
                .postId(question.getId())
                .title(question.getTitle())
                .body(question.getBody())
                .answers(answers)
                .authorId(question.getAuthor().getId())
                .tags(tags)
                .score(question.getScore())
                .postStatus(question.getPostStatus().toString())
                .createdAt(question.getCreatedAt())
                .hasVoted(hasVoted)
                .comments(comments)
                .build();
    }
}
