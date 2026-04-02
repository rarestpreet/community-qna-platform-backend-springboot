package com.project.hearmeout_backend.dto.request.security_request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequestDTO {

    @NotBlank(message = "Username is required")
    @Length(max = 20, message = "Username must be less than 20 character")
    @Schema(description = "unique username for user creation")
    private String username;

    @NotBlank(message = "Password is required")
    @Length(min = 8, message = "Password must be 8 character long")
    @Schema(description = "password for user creation")
    private String password;

    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Email must be valid"
    )
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    @Schema(description = "unique email for user creation")
    private String email;
}
