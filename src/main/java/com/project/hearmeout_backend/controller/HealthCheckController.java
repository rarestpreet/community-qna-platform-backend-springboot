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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/health")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Health Check APIs", description = "Endpoints for system health and configuration checks (Admin only)")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("isFullyAuthenticated() && hasAuthority('ADMIN')")
public class HealthCheckController {

    private final CookieProperties cookieProperties;

    @Operation(summary = "Check system status", description = "Returns basic system status and current timestamp.")
    @GetMapping
    public ResponseEntity<?> healthCheck() {
        log.info("Health check successful");

        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "timestamp", System.currentTimeMillis()));
    }

    @Operation(summary = "Add CORS test cookie", description = "Sets a test cookie to verify CORS and cookie configuration.")
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

    @Operation(summary = "Check CORS status", description = "Checks if the previously set CORS test cookie is received correctly.")
    @GetMapping("/cors")
    public ResponseEntity<?> corsHealthCheck(HttpServletRequest request) {
        String cookieValue = "";
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("corsCheck")) {

                    if (cookie.getValue().isEmpty()) {
                        break;
                    }
                    cookieValue = cookie.getValue();

                    return ResponseEntity.ok(Map.of(
                            "status", "CORS UP",
                            "cookies", cookieValue,
                            "origin", request.getHeader("Origin"),
                            "timestamp", System.currentTimeMillis()));
                }
            }
        }

        return ResponseEntity.ok(Map.of(
                "status", "CORS DOWN",
                "cookies", "",
                "origin", request.getHeader("Origin"),
                "timestamp", System.currentTimeMillis()));
    }
}