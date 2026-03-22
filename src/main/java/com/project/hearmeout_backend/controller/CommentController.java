package com.project.hearmeout_backend.controller;

import com.project.hearmeout_backend.dto.request.comment_request.CommentRequestDTO;
import com.project.hearmeout_backend.exception.CommentNotFoundException;
import com.project.hearmeout_backend.exception.PostNotFoundException;
import com.project.hearmeout_backend.exception.UserNotFoundException;
import com.project.hearmeout_backend.service.CommentService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<@NonNull String> postComment(
            @Valid @RequestBody CommentRequestDTO commentRequestDTO
    ) throws UserNotFoundException, PostNotFoundException {
        commentService.createNewComment(commentRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("Comment was added successfully");
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<@NonNull String> deleteComment(
            @PathVariable Long commentId
    ) throws CommentNotFoundException {
        commentService.removeComment(commentId);

        return ResponseEntity.status(HttpStatus.OK).body("Comment was deleted successfully");
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<@NonNull String> updateComment(
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequestDTO commentRequestDTO
    ) throws CommentNotFoundException {
        commentService.updateCommentBody(commentId, commentRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body("Comment was updated successfully");
    }
}
