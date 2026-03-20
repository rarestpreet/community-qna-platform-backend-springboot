package com.project.hearmeout_backend.controller;

import com.project.hearmeout_backend.dto.request.post_request.AnswerSubmitRequestDTO;
import com.project.hearmeout_backend.dto.request.post_request.QuestionSubmitRequestDTO;
import com.project.hearmeout_backend.dto.response.post_response.QuestionPostResponseDTO;
import com.project.hearmeout_backend.exception.PostNotFoundException;
import com.project.hearmeout_backend.exception.UserNotFoundException;
import com.project.hearmeout_backend.service.PostService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/ask")
    public ResponseEntity<@NonNull String> askQuestion(@Valid @RequestBody QuestionSubmitRequestDTO questionSubmitRequestDTO)
            throws UserNotFoundException {
        postService.postNewQuestion(questionSubmitRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("Question created successfully");
    }

    @PostMapping("/{postId}/answer")
    public ResponseEntity<@NonNull String> submitAnswer(@PathVariable Long postId,
                                                         @Valid @RequestBody AnswerSubmitRequestDTO answerSubmitRequestDTO)
            throws UserNotFoundException, PostNotFoundException {
        postService.postNewAnswer(postId, answerSubmitRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("Answer created successfully");
    }

    @GetMapping("/{postId}")
    public ResponseEntity<@NonNull QuestionPostResponseDTO> getQuestion(
            @PathVariable Long postId,
            @RequestParam(required = false) Long currUserId
    ) throws PostNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getQuestionPost(postId, currUserId));
    }


}
