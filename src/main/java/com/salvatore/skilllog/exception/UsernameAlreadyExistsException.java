package com.salvatore.skilllog.exception;

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException(String username) {
        super("Username gia' registrato: " + username);
    }
}
