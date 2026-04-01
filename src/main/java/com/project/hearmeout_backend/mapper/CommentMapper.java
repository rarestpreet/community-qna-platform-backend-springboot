package com.project.hearmeout_backend.mapper;

import com.project.hearmeout_backend.dto.request.comment_request.CommentRequestDTO;
import com.project.hearmeout_backend.dto.response.comment_response.CommentResponseDTO;
import com.project.hearmeout_backend.dto.response.user_response.UserCommentResponseDTO;
import com.project.hearmeout_backend.model.Comment;
import com.project.hearmeout_backend.model.Post;
import com.project.hearmeout_backend.model.User;

import java.util.Objects;

public class CommentMapper {
    public static UserCommentResponseDTO toUserCommentResponseDto(Comment comment) {
        return UserCommentResponseDTO.builder()
                .id(comment.getId())
                .createdAt(comment.getCreatedAt())
                .body(comment.getBody())
                .build();
    }

    public static Comment toCommentEntity(CommentRequestDTO commentRequestDTO, Post post, User author) {
        return Comment.builder()
                .post(post)
                .author(author)
                .body(commentRequestDTO.getBody())
                .build();
    }

    public static CommentResponseDTO toCommentResponseDTO(Comment comment, Long currUserId) {
        return CommentResponseDTO.builder()
                .commentId(comment.getId())
                .body(comment.getBody())
                .authorId(comment.getAuthor().getId())
                .postId(comment.getPost().getId())
                .updatedAt(comment.getUpdatedAt())
                .isEditable(Objects.equals(comment.getAuthor().getId(), currUserId))
                .build();
    }
}
