package com.project.hearmeout_backend.controller;

import com.project.hearmeout_backend.dto.request.vote_request.VoteRequestDTO;
import com.project.hearmeout_backend.model.Vote;
import com.project.hearmeout_backend.service.VoteService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping("/vote")
    public ResponseEntity<@NonNull String> vote(@RequestBody VoteRequestDTO voteRequestDTO) {
        voteService.handleVote(voteRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body("Vote has been updated");
    }
}
