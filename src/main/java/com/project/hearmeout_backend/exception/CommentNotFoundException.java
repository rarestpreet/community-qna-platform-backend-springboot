package com.project.hearmeout_backend.exception;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(String message) {
        super(message);
    }
}
