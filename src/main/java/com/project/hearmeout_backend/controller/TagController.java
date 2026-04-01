package com.project.hearmeout_backend.controller;

import com.project.hearmeout_backend.dto.request.tag_request.TagCreationRequestDTO;
import com.project.hearmeout_backend.dto.response.tag_response.TagResponseDTO;
import com.project.hearmeout_backend.service.TagService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
@NullMarked
public class TagController {
    private final TagService tagService;

    @GetMapping("")
    public ResponseEntity<List<TagResponseDTO>> tagList() {
        return ResponseEntity.status(HttpStatus.OK).body(tagService.getAllTags());
    }

    // in future make it admin only
    @PreAuthorize("isAuthenticated()")
    @PostMapping("")
    public ResponseEntity<String> createTag(@RequestBody TagCreationRequestDTO tag) {
        tagService.createNewTag(tag);
        return ResponseEntity.status(HttpStatus.OK).body("tag created successfully");
    }
}
