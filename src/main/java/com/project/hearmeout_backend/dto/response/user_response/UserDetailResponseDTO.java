package com.project.hearmeout_backend.dto.response.user_response;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
public class UserDetailResponseDTO {
    private final Long userId;
    private final String username;
    private final String password;
    private final SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");

    public UserDetailResponseDTO(Long id, String email, String password) {
        this.userId = id;
        this.username = email;
        this.password = password;
    }
}
