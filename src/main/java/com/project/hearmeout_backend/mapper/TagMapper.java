package com.project.hearmeout_backend.mapper;

import com.project.hearmeout_backend.dto.response.tag_response.TagResponseDTO;
import com.project.hearmeout_backend.model.Tag;

public class TagMapper {

    public static TagResponseDTO toTagResponseDTO(Tag tag) {
        return TagResponseDTO.builder()
                .tagId(tag.getId())
                .name(tag.getName())
                .description(tag.getDescription())
                .build();
    }
}
