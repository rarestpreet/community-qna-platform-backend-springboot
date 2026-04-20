package com.project.hearmeout_backend.service;

import com.project.hearmeout_backend.dto.response.user_response.UserDetailResponseDTO;
import com.project.hearmeout_backend.model.CustomUserDetails;
import com.project.hearmeout_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@NullMarked
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailResponseDTO currUser = userRepo.findUserForAuth(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found. Enter registered email"));


        return new CustomUserDetails(currUser);
    }
}