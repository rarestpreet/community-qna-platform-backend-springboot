package com.project.hearmeout_backend.service;

import com.project.hearmeout_backend.dto.response.post_response.FeedPostResponseDTO;
import com.project.hearmeout_backend.dto.response.user_response.HomeUserProfileResponseDTO;
import com.project.hearmeout_backend.mapper.PostMapper;
import com.project.hearmeout_backend.mapper.UserMapper;
import com.project.hearmeout_backend.model.Post;
import com.project.hearmeout_backend.model.enums.PostType;
import com.project.hearmeout_backend.repository.PostRepository;
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
    private final UserService userService;

    public List<FeedPostResponseDTO> generateFeed(int page, Long userId) {
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

    public HomeUserProfileResponseDTO getUserProfile(Long userId) {
        if(userId==null){
            return new HomeUserProfileResponseDTO(null, null, false);
        }
        return UserMapper.toHomeUserProfileResponseDTO(userService.checkAndGetUserByUserId(userId));
    }
}
