package com.project.hearmeout_backend.exception;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(String message){
        super(message);
    }
}
