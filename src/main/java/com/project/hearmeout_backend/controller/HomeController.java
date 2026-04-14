package com.project.hearmeout_backend.controller;

import com.project.hearmeout_backend.dto.response.post_response.FeedPostResponseDTO;
import com.project.hearmeout_backend.dto.response.user_response.HomeUserProfileResponseDTO;
import com.project.hearmeout_backend.model.CustomUserDetails;
import com.project.hearmeout_backend.service.HomeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Tag(name = "Home display APIs")
public class HomeController {
    private final HomeService homeService;

    // add search feature without filters

    @Operation(summary = "Get a paginated feed of questions")
    @GetMapping("")
    public ResponseEntity<@NonNull List<FeedPostResponseDTO>> getQuestions(
            @RequestParam(defaultValue = "0") int pageNum,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(homeService.generateFeed(pageNum, userDetails == null ? null : userDetails.getUserId()));
    }

    @Operation(summary = "Get the current authenticated user's profile info for the home page")
    @GetMapping("profile")
    public ResponseEntity<@NonNull HomeUserProfileResponseDTO> getProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(homeService.getUserProfile(userDetails == null ? null : userDetails.getUserId()));
    }
}
