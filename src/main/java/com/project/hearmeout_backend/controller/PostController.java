package com.project.hearmeout_backend.controller;

import com.project.hearmeout_backend.dto.request.post_request.AcceptAnswerRequestDTO;
import com.project.hearmeout_backend.dto.request.post_request.AnswerSubmitRequestDTO;
import com.project.hearmeout_backend.dto.request.post_request.QuestionSubmitRequestDTO;
import com.project.hearmeout_backend.dto.response.post_response.QuestionPostResponseDTO;
import com.project.hearmeout_backend.exception.PostNotFoundException;
import com.project.hearmeout_backend.exception.TagNotFoundException;
import com.project.hearmeout_backend.exception.UserNotFoundException;
import com.project.hearmeout_backend.model.CustomUserDetails;
import com.project.hearmeout_backend.service.implementation.PostServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Tag(name = "Post CRUD APIs")
public class PostController {

    private final PostServiceImpl postServiceImpl;

    @Operation(summary = "Ask a new question")
    @PostMapping("/ask")
    @PreAuthorize("isFullyAuthenticated() && !hasAuthority('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<@NonNull String> askQuestion(@Valid @RequestBody QuestionSubmitRequestDTO questionSubmitRequestDTO,
                                                       @AuthenticationPrincipal CustomUserDetails userDetails)
            throws UserNotFoundException, TagNotFoundException {
        postServiceImpl.postNewQuestion(questionSubmitRequestDTO, userDetails.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body("Question created successfully");
    }

    @Operation(summary = "Submit an answer to a question")
    @PostMapping("/{postId}/answer")
    @PreAuthorize("isFullyAuthenticated() && !hasAuthority('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<@NonNull String> submitAnswer(
            @PathVariable Long postId,
            @Valid @RequestBody AnswerSubmitRequestDTO answerSubmitRequestDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails)
            throws UserNotFoundException, PostNotFoundException {
        postServiceImpl.postNewAnswer(postId, answerSubmitRequestDTO, userDetails.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body("Answer created successfully");
    }

    @Operation(summary = "Get a specific question by ID")
    @GetMapping("/{postId}")
    public ResponseEntity<@NonNull QuestionPostResponseDTO> getQuestion(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) throws PostNotFoundException {
        Long userId = userDetails == null ? 0L : userDetails.getUserId();
        String username = userDetails == null ? "" : userDetails.getUserName();

        return ResponseEntity.status(HttpStatus.OK)
                .body(postServiceImpl.getQuestionPost(postId, userId, username));
    }

    @Operation
    @PostMapping("/toggleStatus")
    @PreAuthorize("isFullyAuthenticated() && !hasAuthority('ADMIN')")
    public ResponseEntity<@NonNull String> toggleAnswerStatus(
            @Valid @RequestBody AcceptAnswerRequestDTO acceptAnswerRequestDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        postServiceImpl.handleAnswerStatus(acceptAnswerRequestDTO, userDetails.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body("Answer accepted successfully");
    }

    @Operation
    @PutMapping("/answer/{answerId}")
    @PreAuthorize("isFullyAuthenticated() && !hasAuthority('ADMIN')")
    public ResponseEntity<@NonNull String> modifyAnswer(
            @PathVariable Long answerId,
            @Valid @RequestBody AnswerSubmitRequestDTO answerSubmitRequestDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        postServiceImpl.updateAnswer(answerId, answerSubmitRequestDTO, userDetails.getUserId());

        return ResponseEntity.status(HttpStatus.OK).body("Answer modified successfully");
    }

    @Operation
    @DeleteMapping("/answer/{answerId}")
    @PreAuthorize("isFullyAuthenticated() && !hasAuthority('ADMIN')")
    public ResponseEntity<@NonNull String> removeAnswer(
            @PathVariable Long answerId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        postServiceImpl.deleteAnswer(answerId, userDetails.getUserId());

        return ResponseEntity.status(HttpStatus.OK).body("Answer deleted successfully");
    }

    @Operation
    @PutMapping("/question/{questionId}")
    @PreAuthorize("isFullyAuthenticated() && !hasAuthority('ADMIN')")
    public ResponseEntity<@NonNull String> modifyQuestion(
            @PathVariable Long questionId,
            @Valid @RequestBody QuestionSubmitRequestDTO questionSubmitRequestDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        postServiceImpl.updateQuestion(questionId, questionSubmitRequestDTO, userDetails.getUserId());

        return ResponseEntity.status(HttpStatus.OK).body("Question modified successfully");
    }

    @Operation
    @DeleteMapping("/question/{questionId}")
    @PreAuthorize("isFullyAuthenticated() && !hasAuthority('ADMIN')")
    public ResponseEntity<@NonNull String> removeQuestion(
            @PathVariable Long questionId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        postServiceImpl.deleteQuestion(questionId, userDetails.getUserId());

        return ResponseEntity.status(HttpStatus.OK).body("Question deleted successfully");
    }
}
