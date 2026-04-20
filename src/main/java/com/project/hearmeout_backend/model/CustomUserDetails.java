package com.project.hearmeout_backend.model;

import com.project.hearmeout_backend.dto.response.user_response.UserDetailResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Slf4j
@NullMarked
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UserDetailResponseDTO user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("getAuthorities {}", user.getRoles().toString());
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .toList();
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
