package com.project.hearmeout_backend.controller;

import com.project.hearmeout_backend.dto.request.security_request.LoginRequestDTO;
import com.project.hearmeout_backend.dto.request.security_request.RegisterRequestDTO;
import com.project.hearmeout_backend.exception.EmailAlreadyExistException;
import com.project.hearmeout_backend.exception.UsernameAlreadyExistException;
import com.project.hearmeout_backend.service.SecurityService;
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
@RequestMapping("/")
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService securityService;

    @PostMapping("register")
    public ResponseEntity<@NonNull String> registerUser(@Valid @RequestBody RegisterRequestDTO registerRequestDTO)
            throws UsernameAlreadyExistException, EmailAlreadyExistException {
        securityService.createNewUser(registerRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @PostMapping("logout")
    public ResponseEntity<@NonNull String> logoutUser() {
        ResponseCookie cookie = securityService.terminateSession();

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Session ended successfully");
    }

    @PostMapping("login")
    public ResponseEntity<@NonNull String> loginUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        String message = securityService.authenticateUser(loginRequestDTO);

        return ResponseEntity.status(HttpStatus.OK)
                // .header(HttpHeaders.SET_COOKIE, cookie)
                .body(message);
    }

}