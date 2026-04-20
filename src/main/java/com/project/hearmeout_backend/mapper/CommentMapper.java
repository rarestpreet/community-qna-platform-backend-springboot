package com.project.hearmeout_backend.mapper;

import com.project.hearmeout_backend.dto.request.comment_request.CommentRequestDTO;
import com.project.hearmeout_backend.model.Comment;
import com.project.hearmeout_backend.model.Post;
import com.project.hearmeout_backend.model.User;

public class CommentMapper {

    public static Comment toCommentEntity(CommentRequestDTO commentRequestDTO, Post post, User author) {
        return Comment.builder()
                .post(post)
                .author(author)
                .body(commentRequestDTO.getBody())
                .build();
    }
}
