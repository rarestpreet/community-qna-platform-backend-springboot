package com.project.hearmeout_backend.controller;

import com.project.hearmeout_backend.config.CookieProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/health")
@Slf4j
@RequiredArgsConstructor
public class HealthCheckController {

    private final CookieProperties cookieProperties;

    @GetMapping
    public ResponseEntity<?> healthCheck() {
        log.info("Health check successful");

        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "timestamp", System.currentTimeMillis()
        ));
    }

    @PostMapping("/cors")
    public ResponseEntity<?> addCookie() {
        ResponseCookie cookie = ResponseCookie.from("corsCheck", "Working")
                .httpOnly(true)
                .path("/")
                .secure(cookieProperties.isSecure())
                .sameSite(cookieProperties.getSameSite())
                .maxAge(TimeUnit.MINUTES.toSeconds(5))
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("cookie sent");
    }

    @GetMapping("/cors")
    public ResponseEntity<?> corsHealthCheck(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        List<String> cookieNames = new ArrayList<>();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookieNames.add(cookie.getName());

                log.info("CORS check successful");
                if (cookie.getName().equals("corsCheck")) {
                    return ResponseEntity.ok(Map.of(
                            "status", "CORS UP",
                            "cookies", cookieNames,
                            "origin", request.getHeader("Origin"),
                            "timestamp", System.currentTimeMillis()
                    ));
                }
            }
        }

        log.warn("CORS check failed");
        return ResponseEntity.ok(Map.of(
                "status", "CORS DOWN",
                "cookies", cookieNames,
                "origin", request.getHeader("Origin"),
                "timestamp", System.currentTimeMillis()
        ));
    }
}