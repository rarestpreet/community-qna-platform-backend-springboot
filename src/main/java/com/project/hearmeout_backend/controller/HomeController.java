package com.project.hearmeout_backend.controller;

import com.project.hearmeout_backend.dto.response.post_response.FeedPostResponseDTO;
import com.project.hearmeout_backend.service.HomeService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    // add search feature without filters

    @GetMapping("")
    public ResponseEntity<@NonNull List<FeedPostResponseDTO>> getQuestions(
            @RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(required = false) Integer userId) {
        return ResponseEntity.status(HttpStatus.OK).body(homeService.generateFeed(pageNum, userId));
    }
}
