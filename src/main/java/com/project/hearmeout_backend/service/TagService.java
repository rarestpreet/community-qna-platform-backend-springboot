package com.project.hearmeout_backend.service;

import com.project.hearmeout_backend.dto.request.tag_request.TagCreationRequestDTO;
import com.project.hearmeout_backend.dto.response.tag_response.TagResponseDTO;
import com.project.hearmeout_backend.mapper.TagMapper;
import com.project.hearmeout_backend.model.Tag;
import com.project.hearmeout_backend.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<TagResponseDTO> getAllTags() {
        List<Tag> tags = tagRepository.findAll();

        return tags.stream()
                .map(TagMapper::toTagResponseDTO)
                .toList();
    }

    @Transactional
    public void createNewTag(TagCreationRequestDTO tag) {
        tagRepository.save(TagMapper.toTagEntity(tag));
    }
}
