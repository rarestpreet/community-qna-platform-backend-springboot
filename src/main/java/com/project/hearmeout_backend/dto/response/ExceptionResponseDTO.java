package com.project.hearmeout_backend.dto.response;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class ExceptionResponseDTO {
    @Schema(description = "HTTP status code")
    private int status;

    @Schema(description = "timestamp of the exception")
    private LocalDateTime timestamp;

    @Schema(description = "error type or reason")
    private String error;

    @Schema(description = "detailed error message")
    private String message;

    @Schema(description = "map of field names to validation error messages")
    private Map<String, String> fieldErrors;
}
