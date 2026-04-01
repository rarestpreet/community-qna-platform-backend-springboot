package com.project.hearmeout_backend.controller;

import com.project.hearmeout_backend.dto.request.comment_request.CommentRequestDTO;
import com.project.hearmeout_backend.exception.CommentNotFoundException;
import com.project.hearmeout_backend.exception.PostNotFoundException;
import com.project.hearmeout_backend.exception.UserNotFoundException;
import com.project.hearmeout_backend.model.CustomUserDetails;
import com.project.hearmeout_backend.service.CommentService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<@NonNull String> postComment(
            @Valid @RequestBody CommentRequestDTO commentRequestDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) throws UserNotFoundException, PostNotFoundException {
        commentService.createNewComment(commentRequestDTO, userDetails.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body("Comment was added successfully");
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<@NonNull String> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal CustomUserDetails userDetails

    ) throws CommentNotFoundException {
        commentService.removeComment(commentId, userDetails.getUserId());

        return ResponseEntity.status(HttpStatus.OK).body("Comment was deleted successfully");
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<@NonNull String> updateComment(
            @PathVariable Long commentId,
            @RequestBody String body,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) throws CommentNotFoundException {
        commentService.updateCommentBody(commentId, body, userDetails.getUserId());

        return ResponseEntity.status(HttpStatus.OK).body("Comment was updated successfully");
    }
}
