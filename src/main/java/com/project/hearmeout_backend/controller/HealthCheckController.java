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
        String cookieValue = "";
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("corsCheck")) {
                    log.info("CORS check successful");
                    cookieValue = cookie.getValue();

                    return ResponseEntity.ok(Map.of(
                            "status", "CORS UP",
                            "cookies", cookieValue,
                            "origin", request.getHeader("Origin"),
                            "timestamp", System.currentTimeMillis()
                    ));
                }
            }
        }

        log.warn("CORS check failed");
        return ResponseEntity.ok(Map.of(
                "status", "CORS DOWN",
                "cookies", cookieValue,
                "origin", request.getHeader("Origin"),
                "timestamp", System.currentTimeMillis()
        ));
    }
}