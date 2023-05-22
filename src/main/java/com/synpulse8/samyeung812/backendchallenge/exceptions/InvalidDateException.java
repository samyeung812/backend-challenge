package com.synpulse8.samyeung812.backendchallenge.exceptions;

public class InvalidDateException extends Exception {
    public InvalidDateException(String dateFormat) {
        super("Invalid date format (" + dateFormat + ")");
    }
}
