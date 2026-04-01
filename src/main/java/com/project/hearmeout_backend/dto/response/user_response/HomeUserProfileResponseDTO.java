package com.project.hearmeout_backend.dto.response.user_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class HomeUserProfileResponseDTO {
        private String username;
        private Long userId;
        private boolean isAccountVerified;
}
