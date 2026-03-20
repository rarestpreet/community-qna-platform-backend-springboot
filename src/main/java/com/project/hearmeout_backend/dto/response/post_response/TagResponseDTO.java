package com.project.hearmeout_backend.dto.response.post_response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagResponseDTO {
    private Long id;
    private String name;
    private String description;
}
