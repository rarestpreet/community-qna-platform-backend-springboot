package com.project.hearmeout_backend.dto.response.tag_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@AllArgsConstructor
@Builder
public class TagResponseDTO {
    @Schema(description = "unique identifier of the tag")
    private Long tagId;

    @Schema(description = "name of the tag")
    private String name;

    @Schema(description = "description of the tag")
    private String description;
}