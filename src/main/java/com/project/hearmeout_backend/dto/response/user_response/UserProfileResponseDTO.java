package com.project.hearmeout_backend.dto.response.user_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class UserProfileResponseDTO {
    @Schema(description = "unique identifier of the user")
    private Long id;

    @Schema(description = "username of the user")
    private String username;

    @Schema(description = "reputation score of the user")
    private int reputation;

    @Schema(description = "timestamp of when the user account was created")
    private LocalDateTime createdAt;

    @Schema(description = "indicates if the user's account is operable")
    private boolean isOperable;
}
