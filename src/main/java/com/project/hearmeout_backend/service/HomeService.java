package com.project.hearmeout_backend.service;

import com.project.hearmeout_backend.dto.response.post_response.FeedPostResponseDTO;
import com.project.hearmeout_backend.dto.response.user_response.HomeUserProfileResponseDTO;
import com.project.hearmeout_backend.mapper.PostMapper;
import com.project.hearmeout_backend.mapper.UserMapper;
import com.project.hearmeout_backend.model.Post;
import com.project.hearmeout_backend.model.enums.PostType;
import com.project.hearmeout_backend.model.enums.RoleType;
import com.project.hearmeout_backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.hearmeout_backend.dto.response.tag_response.TagResponseDTO;
import com.project.hearmeout_backend.mapper.TagMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final PostRepository postRepo;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<FeedPostResponseDTO> generateFeed(int page, Long userId) {
        Pageable pageable = PageRequest.of(page, 10);
        List<Post> questions = new ArrayList<>();

        if (userId != null)
            questions = postRepo.findByPostTypeAndAuthorIdNot(PostType.QUESTION, pageable, userId);
        else
            questions = postRepo.findByPostType(PostType.QUESTION, pageable);

        return questions.stream()
                .map(question -> {
                    List<TagResponseDTO> tags = question.getTags().stream()
                            .map(TagMapper::toTagResponseDTO)
                            .toList();
                    return PostMapper.toFeedPostResponseDTO(question, tags);
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public HomeUserProfileResponseDTO getUserProfile(Long userId) {
        if (userId == null) {
            return new HomeUserProfileResponseDTO(null, null, false, "");
        }
        var user = userService.checkAndGetUserByUserId(userId);
        String role = user.getRoles().contains(RoleType.ADMIN) ? "ADMIN" : "USER";
        return UserMapper.toHomeUserProfileResponseDTO(user, role);
    }
}
