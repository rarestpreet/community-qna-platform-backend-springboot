package com.project.hearmeout_backend.service;

import com.project.hearmeout_backend.dto.request.security_request.LoginRequestDTO;
import com.project.hearmeout_backend.dto.request.security_request.RegisterRequestDTO;
import com.project.hearmeout_backend.exception.EmailAlreadyExistException;
import com.project.hearmeout_backend.exception.UsernameAlreadyExistException;
import com.project.hearmeout_backend.mapper.UserMapper;
import com.project.hearmeout_backend.model.User;
import com.project.hearmeout_backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final UserRepository userRepo;
    private final AuthenticationManager authManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;
    private final JwtService jwtService;

    @Transactional
    public void createNewUser(RegisterRequestDTO registerRequestDTO)
            throws UsernameAlreadyExistException, EmailAlreadyExistException {
        if (userRepo.existsByUsername(registerRequestDTO.getUsername())) {
            throw new UsernameAlreadyExistException("Username is already taken");
        }
        if (userRepo.existsByEmail(registerRequestDTO.getEmail())) {
            throw new EmailAlreadyExistException("Email is already registered");
        }

        User user = UserMapper.toProfileEntity(registerRequestDTO,
                passwordEncoder.encode(registerRequestDTO.getPassword()));

        userRepo.save(user);
    }

    public ResponseCookie terminateSession() {
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();

        return ResponseCookie.from("token", "")
                .httpOnly(true)
                .maxAge(0)
                .secure(false)
                .path("/")
                .sameSite("Lax")
                .build();
    }

    public ResponseCookie authenticateUser(@Valid LoginRequestDTO loginRequestDTO) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);

        // Explicitly save the SecurityContext to the session so Spring Security 6 persists it (auto persistence removed)
        new HttpSessionSecurityContextRepository()
                .saveContext(SecurityContextHolder.getContext(), httpServletRequest, httpServletResponse);

        String jwtToken = jwtService.generateJwtToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());

        // change secure value to true for prod
        return ResponseCookie.from("token", jwtToken)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .maxAge(TimeUnit.MINUTES.toSeconds(30))
                .build();
    }
}
