package com.project.hearmeout_backend.dto.response.user_response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserProfileResponseDTO {
    @Schema(description = "unique identifier of the user")
    private Long userId;

    @Schema(description = "username of the user")
    private String username;

    @Schema(description = "email of the user")
    private String email;

    @Schema(description = "reputation score of the user")
    private int reputation;

    @Schema(description = "timestamp of when the user account was created")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime createdAt;

    @Schema(description = "indicates if the user's account is operable")
    private boolean isOperable;

    @Schema(description = "indicates if the user's account is verified")
    private boolean isAccountVerified;

    @Schema(description = "indicated if the user's account is still active or not")
    private boolean isAccountTerminated;

    public UserProfileResponseDTO(Long userId, String username, String email, int reputation, LocalDateTime createdAt, boolean isAccountVerified, boolean isAccountTerminated) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.reputation = reputation;
        this.createdAt = createdAt;
        this.isAccountVerified = isAccountVerified;
        this.isAccountTerminated = isAccountTerminated;
        this.isOperable = false;
    }
}
