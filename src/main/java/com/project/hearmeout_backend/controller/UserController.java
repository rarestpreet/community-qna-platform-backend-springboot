package com.project.hearmeout_backend.controller;

import com.project.hearmeout_backend.dto.request.user_request.UserProfileModificationRequestDTO;
import com.project.hearmeout_backend.dto.response.user_response.UserAnswerResponseDTO;
import com.project.hearmeout_backend.dto.response.user_response.UserCommentResponseDTO;
import com.project.hearmeout_backend.dto.response.user_response.UserProfileResponseDTO;
import com.project.hearmeout_backend.dto.response.user_response.UserQuestionResponseDTO;
import com.project.hearmeout_backend.exception.EmailAlreadyExistException;
import com.project.hearmeout_backend.exception.UserNotFoundException;
import com.project.hearmeout_backend.exception.UserAlreadyExistException;
import com.project.hearmeout_backend.model.CustomUserDetails;
import com.project.hearmeout_backend.service.implementation.SecurityServiceImpl;
import com.project.hearmeout_backend.service.implementation.UserServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile/{username}")
@RequiredArgsConstructor
@Tag(name = "Profile showcase APIs")
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final SecurityServiceImpl securityServiceImpl;

    @Operation(summary = "Get user profile by username")
    @GetMapping("")
    public ResponseEntity<@NonNull UserProfileResponseDTO> userProfile(@PathVariable String username,
                                                                       @AuthenticationPrincipal CustomUserDetails userDetails)
            throws UserNotFoundException {
        UserProfileResponseDTO profile = userServiceImpl.getUserProfile(username, userDetails == null ? null : userDetails.getUserId());

        return ResponseEntity.status(HttpStatus.OK).body(profile);
    }

    // add pagination and sorting (from recent to older)
    @Operation(summary = "Get all questions asked by a user")
    @GetMapping("/questions")
    public ResponseEntity<@NonNull List<UserQuestionResponseDTO>> userQuestions(@PathVariable String username)
            throws UserNotFoundException {
        List<UserQuestionResponseDTO> userQuestions = userServiceImpl.getUserQuestions(username);

        return ResponseEntity.status(HttpStatus.OK).body(userQuestions);
    }

    // add pagination and sorting (from recent to older)
    @Operation(summary = "Get all answers provided by a user")
    @GetMapping("/answers")
    public ResponseEntity<@NonNull List<UserAnswerResponseDTO>> userAnswers(@PathVariable String username)
            throws UserNotFoundException {
        List<UserAnswerResponseDTO> userAnswer = userServiceImpl.getUserAnswers(username);

        return ResponseEntity.status(HttpStatus.OK).body(userAnswer);
    }

    // add pagination and sorting (from recent to older)
    @Operation(summary = "Get all comments made by a user")
    @GetMapping("/comments")
    public ResponseEntity<@NonNull List<UserCommentResponseDTO>> userComments(@PathVariable String username)
            throws UserNotFoundException {
        List<UserCommentResponseDTO> comments = userServiceImpl.getUserComments(username);

        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }

    @Operation(summary = "Update a user's profile details")
    @PutMapping("")
    @PreAuthorize("isFullyAuthenticated()")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<@NonNull String> updateUserProfile(@PathVariable String username,
                                                             @Valid @RequestBody UserProfileModificationRequestDTO userProfileModificationRequestDTO,
                                                             @AuthenticationPrincipal CustomUserDetails userDetails)
            throws UserNotFoundException, EmailAlreadyExistException, UserAlreadyExistException {
        userServiceImpl.updateUserDetails(userProfileModificationRequestDTO, userDetails.getUserId());

        ResponseCookie clearedCookie = securityServiceImpl.terminateSession();

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, clearedCookie.toString())
                .body("Details updated Successfully");
    }

    @Operation(summary = "Delete a user account")
    @DeleteMapping("")
    @PreAuthorize("isFullyAuthenticated()")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<@NonNull String> deleteUser(@PathVariable String username,
                                                      @AuthenticationPrincipal CustomUserDetails userDetails)
            throws UserNotFoundException {
        userServiceImpl.terminateUserAccount(userDetails.getUserId());

        ResponseCookie clearedCookie = securityServiceImpl.terminateSession();

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, clearedCookie.toString())
                .body("Account deleted Successfully");
    }
}
