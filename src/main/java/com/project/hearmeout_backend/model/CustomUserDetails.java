package com.project.hearmeout_backend.model;

import com.project.hearmeout_backend.dto.response.user_response.UserDetailResponseDTO;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@NullMarked
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UserDetailResponseDTO user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(user.getAuthority());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public Long getUserId() {
        return user.getUserId();
    }
}
