package com.project.hearmeout_backend.controller;

import com.project.hearmeout_backend.dto.request.vote_request.VoteRequestDTO;
import com.project.hearmeout_backend.model.CustomUserDetails;
import com.project.hearmeout_backend.service.VoteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
@Tag(name = "Vote toggle APIs")
@SecurityRequirement(name = "bearerAuth")
public class VoteController {

    private final VoteService voteService;

    @Operation(summary = "Submit or toggle a vote on a post")
    @PostMapping("/vote")
    public ResponseEntity<@NonNull String> vote(@RequestBody VoteRequestDTO voteRequestDTO,
                                                @AuthenticationPrincipal CustomUserDetails userDetails) {
        voteService.handleVote(voteRequestDTO, userDetails == null ? null : userDetails.getUserId());

        return ResponseEntity.status(HttpStatus.OK).body("Vote has been updated");
    }
}
