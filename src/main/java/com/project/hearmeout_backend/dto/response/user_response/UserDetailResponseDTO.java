package com.project.hearmeout_backend.dto.response.user_response;

import com.project.hearmeout_backend.model.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserDetailResponseDTO {
    private final Long userId;
    private final String username;
    private final String password;
    private List<RoleType> roles;
}
