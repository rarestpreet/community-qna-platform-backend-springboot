package com.project.hearmeout_backend.dto.response.user_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@AllArgsConstructor
@Builder
public class HomeUserProfileResponseDTO {
        @Schema(description = "username of the user")
        private String username;

        @Schema(description = "unique identifier of the user")
        private Long userId;

        @Schema(description = "indicates if the user's account is verified")
        private boolean isAccountVerified;
}
