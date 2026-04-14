package com.project.hearmeout_backend.controller;

import com.project.hearmeout_backend.dto.request.security_request.LoginRequestDTO;
import com.project.hearmeout_backend.dto.request.security_request.RegisterRequestDTO;
import com.project.hearmeout_backend.exception.EmailAlreadyExistException;
import com.project.hearmeout_backend.exception.UsernameAlreadyExistException;
import com.project.hearmeout_backend.service.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
@Tag(name = "Account authentication APIs")
public class SecurityController {

    private final SecurityService securityService;

    @Operation(summary = "Register a new user account")
    @PostMapping("register")
    public ResponseEntity<@NonNull String> registerUser(@Valid @RequestBody RegisterRequestDTO registerRequestDTO)
            throws UsernameAlreadyExistException, EmailAlreadyExistException {
        securityService.createNewUser(registerRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @Operation(summary = "Logout the current user and invalidate the session")
    @PostMapping("logout")
    public ResponseEntity<@NonNull String> logoutUser() {
        ResponseCookie cookie = securityService.terminateSession();

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Session ended successfully");
    }

    @Operation(summary = "Authenticate a user and create a session")
    @PostMapping("login")
    public ResponseEntity<@NonNull String> loginUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        ResponseCookie cookie = securityService.authenticateUser(loginRequestDTO);

        return ResponseEntity.status(HttpStatus.OK)
                 .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("User logged in successfully");
    }

}