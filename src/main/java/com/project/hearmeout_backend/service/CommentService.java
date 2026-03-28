package com.project.hearmeout_backend.service;

import com.project.hearmeout_backend.dto.request.comment_request.CommentRequestDTO;
import com.project.hearmeout_backend.exception.CommentNotFoundException;
import com.project.hearmeout_backend.exception.PostNotFoundException;
import com.project.hearmeout_backend.exception.UserNotFoundException;
import com.project.hearmeout_backend.mapper.CommentMapper;
import com.project.hearmeout_backend.model.Comment;
import com.project.hearmeout_backend.model.Post;
import com.project.hearmeout_backend.model.User;
import com.project.hearmeout_backend.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserService userService;
    private final PostService postService;
    private final CommentRepository commentRepo;

    @Transactional
    public void createNewComment(CommentRequestDTO commentRequestDTO)
            throws UserNotFoundException, PostNotFoundException {
        User author = userService.checkAndGetUserByUserId(commentRequestDTO.getAuthorId());
        Post post = postService.checkAndGetPost(commentRequestDTO.getPostId());

        Comment newComment = CommentMapper.toCommentEntity(commentRequestDTO, post, author);

        commentRepo.save(newComment);
    }

    @Transactional
    public void removeComment(Long commentId)
            throws CommentNotFoundException {
        Comment comment = checkAndGetComment(commentId);

        commentRepo.delete(comment);
    }

    public Comment checkAndGetComment(Long commentId)
            throws CommentNotFoundException {
        return commentRepo.findById(commentId)
                .orElseThrow(() ->
                        new CommentNotFoundException("Comment not found with id: " + commentId)
                );
    }

    @Transactional
    public void updateCommentBody(Long commentId, String body)
            throws CommentNotFoundException {
        Comment comment = checkAndGetComment(commentId);
        comment.setBody(body);

        commentRepo.save(comment);
    }
}
