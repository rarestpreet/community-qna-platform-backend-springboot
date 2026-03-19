package com.project.hearmeout_backend.controller;

import com.project.hearmeout_backend.dto.request.SecurityRequest.RegisterRequestDTO;
import com.project.hearmeout_backend.dto.request.UserRequest.UserProfileRequestDTO;
import com.project.hearmeout_backend.dto.response.UserResponse.UserAnswerResponseDTO;
import com.project.hearmeout_backend.dto.response.UserResponse.UserCommentResponseDTO;
import com.project.hearmeout_backend.dto.response.UserResponse.UserProfileResponseDTO;
import com.project.hearmeout_backend.dto.response.UserResponse.UserQuestionResponseDTO;
import com.project.hearmeout_backend.exception.EmailAlreadyExistException;
import com.project.hearmeout_backend.exception.UserNotFoundException;
import com.project.hearmeout_backend.exception.UsernameAlreadyExistException;
import com.project.hearmeout_backend.service.UserService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile/{username}")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<@NonNull UserProfileResponseDTO> userProfile(@PathVariable String username)
            throws UserNotFoundException {
        UserProfileResponseDTO profile = userService.getUserProfile(username);

        return ResponseEntity.status(HttpStatus.OK).body(profile);
    }

    @GetMapping("/posts")
    public ResponseEntity<@NonNull List<UserQuestionResponseDTO>> userQuestions(@PathVariable String username)
            throws UserNotFoundException {
        List<UserQuestionResponseDTO> userQuestions = userService.getUserQuestions(username);

        return ResponseEntity.status(HttpStatus.OK).body(userQuestions);
    }

    @GetMapping("/answers")
    public ResponseEntity<@NonNull List<UserAnswerResponseDTO>> userAnswers(@PathVariable String username)
            throws UserNotFoundException {
        List<UserAnswerResponseDTO> userAnswer = userService.getUserAnswers(username);

        return ResponseEntity.status(HttpStatus.OK).body(userAnswer);
    }

    @GetMapping("/comments")
    public ResponseEntity<@NonNull List<UserCommentResponseDTO>> userComments(@PathVariable String username)
            throws UserNotFoundException {
        List<UserCommentResponseDTO> comments = userService.getUserComments(username);

        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }

    @PutMapping("/update")
    public ResponseEntity<@NonNull String> updateUserProfile(@PathVariable String username,
                                                             @Valid @RequestBody UserProfileRequestDTO userProfileRequestDTO)
            throws UserNotFoundException, EmailAlreadyExistException, UsernameAlreadyExistException {
        userService.updateUserDetails(username, userProfileRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body("Details updated Successfully");
    }

    @DeleteMapping("/")
    public ResponseEntity<@NonNull String> deleteUser(@PathVariable String username)
            throws UserNotFoundException {
        userService.terminateUserAccount(username);

        return ResponseEntity.status(HttpStatus.OK).body("Account deleted Successfully");
    }
}
