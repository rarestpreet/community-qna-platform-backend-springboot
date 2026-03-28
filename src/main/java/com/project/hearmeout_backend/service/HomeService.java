package com.project.hearmeout_backend.service;

import com.project.hearmeout_backend.dto.response.post_response.FeedPostResponseDTO;
import com.project.hearmeout_backend.dto.response.user_response.HomeUserProfileResponseDTO;
import com.project.hearmeout_backend.mapper.PostMapper;
import com.project.hearmeout_backend.mapper.UserMapper;
import com.project.hearmeout_backend.model.CustomUserDetails;
import com.project.hearmeout_backend.model.Post;
import com.project.hearmeout_backend.model.enums.PostType;
import com.project.hearmeout_backend.repository.PostRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final PostRepository postRepo;
    private final UserService userService;

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

    public @Nullable HomeUserProfileResponseDTO getUserProfile(Authentication authentication) {
        if (authentication == null ||
                !authentication.isAuthenticated() ||
        authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if(!(principal instanceof CustomUserDetails userDetails)) {
            return null;
        }
        String email = userDetails.getUsername();

        return UserMapper.toHomeUserProfileResponseDTO(userService.checkAndGetUserByEmail(email));
    }
}
