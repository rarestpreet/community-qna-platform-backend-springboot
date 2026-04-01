package com.project.hearmeout_backend.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class ExceptionResponseDTO {
    private int status;
    private LocalDateTime timestamp;
    private String error;
    private String message;
    private Map<String, String> fieldErrors;
}
