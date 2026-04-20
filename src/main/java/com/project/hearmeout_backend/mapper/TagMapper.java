package com.project.hearmeout_backend.mapper;

import com.project.hearmeout_backend.dto.request.tag_request.TagCreationRequestDTO;
import com.project.hearmeout_backend.model.Tag;

public class TagMapper {

    public static Tag toTagEntity(TagCreationRequestDTO tag) {
        return Tag.builder()
                .name(tag.getName())
                .description(tag.getDescription())
                .build();
    }
}
