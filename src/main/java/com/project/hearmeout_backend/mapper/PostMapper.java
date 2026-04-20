package com.project.hearmeout_backend.mapper;

import com.project.hearmeout_backend.dto.request.post_request.AnswerSubmitRequestDTO;
import com.project.hearmeout_backend.dto.request.post_request.QuestionSubmitRequestDTO;
import com.project.hearmeout_backend.model.Post;
import com.project.hearmeout_backend.model.Tag;
import com.project.hearmeout_backend.model.User;
import com.project.hearmeout_backend.model.enums.PostStatus;
import com.project.hearmeout_backend.model.enums.PostType;

import java.util.List;

public class PostMapper {

    public static Post answerToPostEntity(AnswerSubmitRequestDTO answerSubmitRequestDTO, Post parent, User author) {
        return Post.builder()
                .title(null)
                .body(answerSubmitRequestDTO.getBody())
                .postType(PostType.ANSWER)
                .parent(parent)
                .author(author)
                .status(PostStatus.UNREVIEWED)
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
                .status(PostStatus.UNANSWERED)
                .build();
    }
}
