package com.project.hearmeout_backend.exception;

import com.project.hearmeout_backend.dto.response.ExceptionResponseDTO;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<@NonNull ExceptionResponseDTO> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException ex) {
        assert ex.getAuthenticationRequest() != null;
        ExceptionResponseDTO response = ExceptionResponseDTO.builder()
                .status(400)
                .error("Internal authentication service exception")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<@NonNull ExceptionResponseDTO> handleUserNotFoundException(UserNotFoundException ex) {
        ExceptionResponseDTO response = ExceptionResponseDTO.builder()
                .status(404)
                .error("User not found")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<@NonNull ExceptionResponseDTO> handleEmailAlreadyExistException(EmailAlreadyExistException ex) {
        ExceptionResponseDTO response = ExceptionResponseDTO.builder()
                .status(409)
                .error("Email already exist")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<@NonNull ExceptionResponseDTO> handlePostNotFoundException(PostNotFoundException ex) {
        ExceptionResponseDTO response = ExceptionResponseDTO.builder()
                .status(404)
                .error("Post not found")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<@NonNull ExceptionResponseDTO> handleCommentNotFoundException(CommentNotFoundException ex) {
        ExceptionResponseDTO response = ExceptionResponseDTO.builder()
                .status(404)
                .error("Comment not found")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UsernameAlreadyExistException.class)
    public ResponseEntity<@NonNull ExceptionResponseDTO> handleUsernameAlreadyExistException(UsernameAlreadyExistException ex) {
        ExceptionResponseDTO response = ExceptionResponseDTO.builder()
                .status(409)
                .error("Username already exist")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<@NonNull ExceptionResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {
        ExceptionResponseDTO response = ExceptionResponseDTO.builder()
                .status(400)
                .error("Validation failed")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<@NonNull ExceptionResponseDTO> handleTagNotFoundException(TagNotFoundException ex) {
        ExceptionResponseDTO response = ExceptionResponseDTO.builder()
                .status(404)
                .error("Tag not found")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<@NonNull ExceptionResponseDTO> handleInvalidPostOperationException(InvalidOperationException ex) {
        ExceptionResponseDTO response = ExceptionResponseDTO.builder()
                .status(409)
                .error("Invalid post operation")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }


}