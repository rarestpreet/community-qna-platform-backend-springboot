package com.project.hearmeout_backend.controller;

import com.project.hearmeout_backend.dto.request.security_request.AccountVerificationRequestDTO;
import com.project.hearmeout_backend.dto.request.security_request.LoginRequestDTO;
import com.project.hearmeout_backend.dto.request.security_request.PasswordResetRequestDTO;
import com.project.hearmeout_backend.dto.request.security_request.RegisterRequestDTO;
import com.project.hearmeout_backend.exception.EmailAlreadyExistException;
import com.project.hearmeout_backend.exception.UserAlreadyExistException;
import com.project.hearmeout_backend.model.CustomUserDetails;
import com.project.hearmeout_backend.service.implementation.SecurityServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
@Tag(name = "Authentication Management", description = "Endpoints for user registration, login, logout, and account verification")
public class SecurityController {

    private final SecurityServiceImpl securityServiceImpl;

    @Operation(summary = "Register a new user account", description = "Registers a new user with the provided details. Fails if the username or email is already taken.")
    @PostMapping("register")
    public ResponseEntity<@NonNull String> registerUser(
            @Valid @RequestBody RegisterRequestDTO registerRequestDTO)
            throws UserAlreadyExistException, EmailAlreadyExistException {
        securityServiceImpl.createNewUser(registerRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @Operation(summary = "Logout user", description = "Logs out the currently authenticated user by invalidating their session cookie.")
    @PostMapping("logout")
    public ResponseEntity<@NonNull String> logoutUser() {
        ResponseCookie cookie = securityServiceImpl.terminateSession();

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Session ended successfully");
    }

    @Operation(summary = "Login user", description = "Authenticates a user with email and password, and sets an HTTP-only session cookie upon success.")
    @PostMapping("login")
    public ResponseEntity<@NonNull String> loginUser(
            @Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        ResponseCookie cookie = securityServiceImpl.authenticateUser(loginRequestDTO);

        return ResponseEntity.status(HttpStatus.OK)
                 .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("User logged in successfully");
    }

    @Operation(summary = "Reset user password", description = "Resets the user's password using a valid OTP and terminates their current session.")
    @PostMapping("password-reset")
    public ResponseEntity<@NonNull String> resetPassword(
            @Valid @RequestBody PasswordResetRequestDTO passwordResetRequestDTO
    ) {
        securityServiceImpl.modifyUserPassword(passwordResetRequestDTO);

        ResponseCookie clearedCookie = securityServiceImpl.terminateSession();

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, clearedCookie.toString())
                .body("Password updated successfully");
    }

    @Operation(summary = "Verify user account", description = "Verifies the user's account using a valid OTP sent to their email. Requires authentication.")
    @PostMapping("verify-account")
    @PreAuthorize("isFullyAuthenticated()")
    public ResponseEntity<@NonNull String> verifyAccount(
            @Valid @RequestBody AccountVerificationRequestDTO accountVerificationRequestDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails
            ) {
        securityServiceImpl.verifyUserEmail(accountVerificationRequestDTO, userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK)
                .body("Account verified successfully");
    }

}