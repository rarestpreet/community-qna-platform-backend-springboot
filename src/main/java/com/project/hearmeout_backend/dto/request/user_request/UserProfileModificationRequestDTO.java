package com.project.hearmeout_backend.dto.request.user_request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileModificationRequestDTO {

    @NotNull(message = "User ID is required")
    @Schema(description = "The unique identifier of the user to be updated")
    private Long userId;

    @NotBlank(message = "Username is required")
    @Length(max = 50, message = "Username must be less than 50 characters")
    @Schema(description = "The new username to set for the user profile")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    @Schema(description = "The new email address to set for the user profile")
    private String email;
}
