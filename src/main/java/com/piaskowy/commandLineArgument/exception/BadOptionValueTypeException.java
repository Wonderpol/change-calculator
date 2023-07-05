package com.piaskowy.commandLineArgument.exception;

public class BadOptionValueTypeException extends RuntimeException {
    public BadOptionValueTypeException(final String message) {
        super(message);
    }
}
