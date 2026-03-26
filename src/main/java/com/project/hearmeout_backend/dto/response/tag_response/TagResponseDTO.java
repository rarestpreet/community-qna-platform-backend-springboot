package com.project.hearmeout_backend.dto.response.tag_response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagResponseDTO {
    private Long tagId;
    private String name;
    private String description;
}