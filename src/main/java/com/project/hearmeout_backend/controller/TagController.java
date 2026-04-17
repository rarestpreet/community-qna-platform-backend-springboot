package com.project.hearmeout_backend.controller;

import com.project.hearmeout_backend.dto.request.tag_request.TagCreationRequestDTO;
import com.project.hearmeout_backend.dto.response.tag_response.TagResponseDTO;
import com.project.hearmeout_backend.service.TagService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Tag CRUD APIS")
@SecurityRequirement(name = "bearerAuth")
public class TagController {
    private final TagService tagService;

    @Operation(summary = "Get a list of all existing tags")
    @GetMapping("")
    public ResponseEntity<List<TagResponseDTO>> tagList() {
        return ResponseEntity.status(HttpStatus.OK).body(tagService.getAllTags());
    }

    @Operation(summary = "Create a new tag")
    @PreAuthorize("isFullyAuthenticated()")
    @PostMapping("")
    public ResponseEntity<String> createTag(@RequestBody TagCreationRequestDTO tag) {
        tagService.createNewTag(tag);
        return ResponseEntity.status(HttpStatus.OK).body("tag created successfully");
    }
}
