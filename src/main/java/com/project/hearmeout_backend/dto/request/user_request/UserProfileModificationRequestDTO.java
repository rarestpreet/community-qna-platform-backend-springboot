package com.project.hearmeout_backend.dto.request.user_request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileModificationRequestDTO {

    @NotBlank(message = "Username is required")
    @Length(max = 50, message = "Username must be less than 50 characters")
    @Schema(description = "new unique username for account")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    @Schema(description = "new unique email for account")
    private String email;
}
