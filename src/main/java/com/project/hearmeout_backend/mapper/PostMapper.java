package com.project.hearmeout_backend.mapper;

import com.project.hearmeout_backend.dto.request.post_request.AnswerSubmitRequestDTO;
import com.project.hearmeout_backend.dto.request.post_request.QuestionSubmitRequestDTO;
import com.project.hearmeout_backend.dto.response.post_response.*;
import com.project.hearmeout_backend.dto.response.user_response.UserAnswerResponseDTO;
import com.project.hearmeout_backend.dto.response.user_response.UserQuestionResponseDTO;
import com.project.hearmeout_backend.model.Post;
import com.project.hearmeout_backend.model.enums.PostType;

import java.util.List;

public class PostMapper {

    public static Post answerToPostEntity(AnswerSubmitRequestDTO answerSubmitRequestDTO) {
        return Post.builder()
                .title(null)
                .body(answerSubmitRequestDTO.getBody())
                .postType(PostType.ANSWER)
                .parent(null)
                .author(null)
                .build();
    }

    public static Post questionToPostEntity(QuestionSubmitRequestDTO questionSubmitRequestDTO) {
        return Post.builder()
                .title(questionSubmitRequestDTO.getTitle())
                .body(questionSubmitRequestDTO.getBody())
                .postType(PostType.QUESTION)
                .author(null)
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
        return FeedPostResponseDTO.builder()
                .postId(question.getId())
                .authorUsername(question.getAuthor().getUsername())
                .authorId(question.getAuthor().getId())
                .title(question.getTitle())
                .score(question.getScore())
                .createdAt(question.getCreatedAt())
                .status(question.getPostStatus().toString())
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

    public static TagResponseDTO toTagResponseDTO(com.project.hearmeout_backend.model.Tag tag) {
        return TagResponseDTO.builder()
                .id(tag.getId())
                .name(tag.getName())
                .description(tag.getDescription())
                .build();
    }

    public static CommentResponseDTO toCommentResponseDTO(com.project.hearmeout_backend.model.Comment comment) {
        return CommentResponseDTO.builder()
                .id(comment.getId())
                .body(comment.getBody())
                .authorUsername(comment.getAuthor().getUsername())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public static QuestionPostResponseDTO toQuestionPostResponseDTO(Post question, boolean hasVoted, Long currUserId) {
        List<FeedAnswerResponseDTO> answers = question.getAnswers().stream()
                .map(answer -> {
                    // Check if the current user voted on this specific answer
                    boolean userVotedOnAnswer = answer.getVotes().stream()
                            .anyMatch(vote -> vote.getUser().getId().equals(currUserId));
                    return PostMapper.toFeedAnswerResponseDTO(answer, userVotedOnAnswer);
                }).toList();

        List<TagResponseDTO> tags = question.getTags().stream()
                .map(PostMapper::toTagResponseDTO)
                .toList();

        List<CommentResponseDTO> comments = question.getComments().stream()
                .map(PostMapper::toCommentResponseDTO)
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
