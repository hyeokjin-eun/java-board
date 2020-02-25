package com.user.java.domain.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User Not Found Exception");
    }
}
