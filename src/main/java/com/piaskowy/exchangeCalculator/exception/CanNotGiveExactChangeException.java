package com.piaskowy.exchangeCalculator.exception;

public class CanNotGiveExactChangeException extends RuntimeException {
    public CanNotGiveExactChangeException(final String message) {
        super(message);
    }
}
