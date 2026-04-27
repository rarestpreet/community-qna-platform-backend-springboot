package com.project.hearmeout_backend.service.implementation;

import com.project.hearmeout_backend.config.CookieProperties;
import com.project.hearmeout_backend.dto.request.security_request.LoginRequestDTO;
import com.project.hearmeout_backend.dto.request.security_request.RegisterRequestDTO;
import com.project.hearmeout_backend.exception.EmailAlreadyExistException;
import com.project.hearmeout_backend.exception.UserAlreadyExistException;
import com.project.hearmeout_backend.mapper.UserMapper;
import com.project.hearmeout_backend.model.CustomUserDetails;
import com.project.hearmeout_backend.model.User;
import com.project.hearmeout_backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityServiceImpl {

    private final UserRepository userRepo;
    private final AuthenticationManager authManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final HttpServletRequest httpServletRequest;
    private final JwtServiceImpl jwtServiceImpl;
    private final CookieProperties cookieProperties;

    @Transactional
    public void createNewUser(RegisterRequestDTO registerRequestDTO)
            throws UserAlreadyExistException, EmailAlreadyExistException {
        if (userRepo.existsByUsernameOrEmail(registerRequestDTO.getUsername(), registerRequestDTO.getEmail())) {
            throw new UserAlreadyExistException("User with similar username or email already exist");
        }

        User user = UserMapper.toProfileEntity(registerRequestDTO,
                passwordEncoder.encode(registerRequestDTO.getPassword()));

        userRepo.save(user);
        log.info("Successfully created new user account for email: {}", registerRequestDTO.getEmail());
    }

    public ResponseCookie terminateSession() {
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        log.info("Terminated user session and cleared security context");

        return ResponseCookie.from("token", "")
                .httpOnly(true)
                .maxAge(0)
                .secure(cookieProperties.isSecure())
                .path("/")
                .sameSite(cookieProperties.getSameSite())
                .build();
    }

    public ResponseCookie authenticateUser(LoginRequestDTO loginRequestDTO) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())
        );

        /*
        -> For session/stateful authentication
        SecurityContextHolder.getContext().setAuthentication(auth);

        -> Explicitly save the SecurityContext to the session so Spring Security 6 persists it (auto persistence removed)
        new HttpSessionSecurityContextRepository()
                .saveContext(SecurityContextHolder.getContext(), httpServletRequest, httpServletResponse);
        */

        CustomUserDetails currUser = (CustomUserDetails) auth.getPrincipal();

        String jwtToken = jwtServiceImpl.generateJwtToken(currUser.getUsername(), currUser.getUserId());
        log.info("Successfully authenticated user: {}", loginRequestDTO.getEmail());

        // change secure value to true for prod
        return ResponseCookie.from("token", jwtToken)
                .path("/")
                .httpOnly(true)
                .secure(cookieProperties.isSecure())
                .sameSite(cookieProperties.getSameSite())
                .maxAge(TimeUnit.MINUTES.toSeconds(30))
                .build();
    }
}
