package com.project.hearmeout_backend.dto.response.tag_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class TagResponseDTO {
    private Long tagId;
    private String name;
    private String description;
}