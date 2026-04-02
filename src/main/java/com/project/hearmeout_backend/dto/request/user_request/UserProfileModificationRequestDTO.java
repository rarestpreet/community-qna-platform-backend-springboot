package com.project.hearmeout_backend.dto.request.user_request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileRequestDTO {

    @NotBlank(message = "Username is required")
    @Length(max = 50, message = "Username must be less than 50 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Length(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    private String email;
}
