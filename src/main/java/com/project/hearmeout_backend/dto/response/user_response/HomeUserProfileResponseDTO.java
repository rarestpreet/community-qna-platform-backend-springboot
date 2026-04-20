package com.project.hearmeout_backend.dto.response.user_response;

import com.project.hearmeout_backend.model.enums.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class HomeUserProfileResponseDTO {
    @Schema(description = "username of the user")
    private String username;

    @Schema(description = "unique identifier of the user")
    private Long userNavigationId;

    @Schema(description = "indicates if the user's account is verified")
    private boolean accountVerified;

    @Schema(description = "Role assigned to the user", example = "USER")
    private List<RoleType> roles;
}
