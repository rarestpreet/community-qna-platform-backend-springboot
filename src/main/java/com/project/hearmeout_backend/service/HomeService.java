package com.project.hearmeout_backend.service;

import com.project.hearmeout_backend.dto.response.post_response.FeedPostResponseDTO;
import com.project.hearmeout_backend.mapper.PostMapper;
import com.project.hearmeout_backend.model.Post;
import com.project.hearmeout_backend.model.enums.PostType;
import com.project.hearmeout_backend.repository.PostRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final PostRepository postRepo;

    public @NonNull List<FeedPostResponseDTO> generateFeed(int page, Integer userId) {
        Pageable pageable = PageRequest.of(page, 10);
        List<Post> questions = new ArrayList<>();

        if(userId!=null)
            questions = postRepo.findByPostTypeAndAuthorIdNot(PostType.QUESTION, pageable, userId);
        else
            questions = postRepo.findByPostType(PostType.QUESTION, pageable);

        return questions.stream()
                .map(PostMapper::toFeedPostResponseDTO)
                .toList();
    }
}
