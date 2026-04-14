package com.project.hearmeout_backend.dto.response.user_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@AllArgsConstructor
@Builder
public class UserProfileResponseDTO {
    @Schema(description = "unique identifier of the user")
    private Long id;

    @Schema(description = "username of the user")
    private String username;

    @Schema(description = "email of the user")
    private String email;

    @Schema(description = "reputation score of the user")
    private int reputation;

    @Schema(description = "timestamp of when the user account was created")
    private String createdAt;

    @Schema(description = "indicates if the user's account is operable")
    private boolean isOperable;

    @Schema(description = "indicates if the user's account is verified")
    private boolean isAccountVerified;
}
