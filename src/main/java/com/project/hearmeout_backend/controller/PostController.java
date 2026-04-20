package com.project.hearmeout_backend.controller;

import com.project.hearmeout_backend.dto.request.post_request.AnswerSubmitRequestDTO;
import com.project.hearmeout_backend.dto.request.post_request.QuestionSubmitRequestDTO;
import com.project.hearmeout_backend.dto.response.post_response.QuestionPostResponseDTO;
import com.project.hearmeout_backend.exception.PostNotFoundException;
import com.project.hearmeout_backend.exception.TagNotFoundException;
import com.project.hearmeout_backend.exception.UserNotFoundException;
import com.project.hearmeout_backend.model.CustomUserDetails;
import com.project.hearmeout_backend.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Tag(name = "Post CRUD APIs")
public class PostController {

    private final PostService postService;

    @Operation(summary = "Ask a new question")
    @PostMapping("/ask")
    @PreAuthorize("isFullyAuthenticated()")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<@NonNull String> askQuestion(@Valid @RequestBody QuestionSubmitRequestDTO questionSubmitRequestDTO,
                                                       @AuthenticationPrincipal CustomUserDetails userDetails)
            throws UserNotFoundException, TagNotFoundException {
        postService.postNewQuestion(questionSubmitRequestDTO, userDetails.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body("Question created successfully");
    }

    @Operation(summary = "Submit an answer to a question")
    @PostMapping("/{postId}/answer")
    @PreAuthorize("isFullyAuthenticated()")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<@NonNull String> submitAnswer(@PathVariable Long postId,
                                                        @Valid @RequestBody AnswerSubmitRequestDTO answerSubmitRequestDTO,
                                                        @AuthenticationPrincipal CustomUserDetails userDetails)
            throws UserNotFoundException, PostNotFoundException {
        postService.postNewAnswer(postId, answerSubmitRequestDTO, userDetails.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body("Answer created successfully");
    }

    @Operation(summary = "Get a specific question by ID")
    @GetMapping("/{postId}")
    public ResponseEntity<@NonNull QuestionPostResponseDTO> getQuestion(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) throws PostNotFoundException {
        Long userId = userDetails == null ? 0L : userDetails.getUserId();
        String username = userDetails == null ? "" : userDetails.getUsername();

        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.getQuestionPost(postId, userId, username));
    }
}
