package com.synpulse8.samyeung812.backendchallenge.exceptions;

public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException(String username) {
        super("User " + username + " already exists");
    }
}
