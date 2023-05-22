package com.synpulse8.samyeung812.backendchallenge.exceptions;

public class CannotGetExchangeRateException extends Exception {
    public CannotGetExchangeRateException() {
        super("Fail to get exchange rate");
    }
}
