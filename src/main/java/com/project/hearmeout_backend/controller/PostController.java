package com.project.hearmeout_backend.controller;

import com.project.hearmeout_backend.dto.request.post_request.AnswerSubmitRequestDTO;
import com.project.hearmeout_backend.dto.request.post_request.QuestionSubmitRequestDTO;
import com.project.hearmeout_backend.dto.response.post_response.QuestionPostResponseDTO;
import com.project.hearmeout_backend.exception.PostNotFoundException;
import com.project.hearmeout_backend.exception.TagNotFoundException;
import com.project.hearmeout_backend.exception.UserNotFoundException;
import com.project.hearmeout_backend.model.CustomUserDetails;
import com.project.hearmeout_backend.service.PostService;
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
public class PostController {

    private final PostService postService;

    // add controller to handle post status (UNANSWERED, CLOSED, etc.) after user interaction

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/ask")
    public ResponseEntity<@NonNull String> askQuestion(@Valid @RequestBody QuestionSubmitRequestDTO questionSubmitRequestDTO,
                                                       @AuthenticationPrincipal CustomUserDetails userDetails)
            throws UserNotFoundException, TagNotFoundException {
        postService.postNewQuestion(questionSubmitRequestDTO, userDetails.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body("Question created successfully");
    }

    // make sure parent of answer is not another answer
    @PostMapping("/{postId}/answer")
    public ResponseEntity<@NonNull String> submitAnswer(@PathVariable Long postId,
                                                        @Valid @RequestBody AnswerSubmitRequestDTO answerSubmitRequestDTO,
                                                        @AuthenticationPrincipal CustomUserDetails userDetails)
            throws UserNotFoundException, PostNotFoundException {
        postService.postNewAnswer(postId, answerSubmitRequestDTO, userDetails.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body("Answer created successfully");
    }

    @GetMapping("/{postId}")
    public ResponseEntity<@NonNull QuestionPostResponseDTO> getQuestion(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) throws PostNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.getQuestionPost(postId, userDetails != null ? userDetails.getUserId() : null));
    }

    // add post (question, answer) update and deletion
}
