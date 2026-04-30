package com.project.hearmeout_backend.controller;

import com.project.hearmeout_backend.dto.request.comment_request.CommentRequestDTO;
import com.project.hearmeout_backend.exception.CommentNotFoundException;
import com.project.hearmeout_backend.exception.PostNotFoundException;
import com.project.hearmeout_backend.exception.UserNotFoundException;
import com.project.hearmeout_backend.model.CustomUserDetails;
import com.project.hearmeout_backend.service.implementation.CommentServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Tag(name = "Comment CRUD APIs")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("isFullyAuthenticated() && !hasAuthority('ADMIN')")
public class CommentController {

    private final CommentServiceImpl commentServiceImpl;

    @Operation(summary = "Post a new comment on a post")
    @PostMapping("")
    public ResponseEntity<@NonNull String> postComment(
            @Valid @RequestBody CommentRequestDTO commentRequestDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) throws UserNotFoundException, PostNotFoundException {
        commentServiceImpl.createNewComment(commentRequestDTO, userDetails.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body("Comment was added successfully");
    }

    @Operation(summary = "Delete an existing comment")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<@NonNull String> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) throws CommentNotFoundException {
        commentServiceImpl.removeComment(commentId, userDetails.getUserId());

        return ResponseEntity.status(HttpStatus.OK).body("Comment was deleted successfully");
    }

    @Operation(summary = "Update an existing comment body")
    @PutMapping("/{commentId}")
    public ResponseEntity<@NonNull String> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDTO commentRequestDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) throws CommentNotFoundException {
        commentServiceImpl.updateCommentBody(commentId, commentRequestDTO.getBody(), userDetails.getUserId());

        return ResponseEntity.status(HttpStatus.OK).body("Comment was updated successfully");
    }
}
