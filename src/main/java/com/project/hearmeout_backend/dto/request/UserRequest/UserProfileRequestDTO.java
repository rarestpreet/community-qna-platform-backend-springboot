package com.project.hearmeout_backend.dto.request.UserRequest;

import lombok.Data;

@Data
public class UserProfileRequestDTO {
    private String username;
    private String password;
    private String email;
}
