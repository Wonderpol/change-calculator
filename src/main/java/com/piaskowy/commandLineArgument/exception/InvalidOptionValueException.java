package com.piaskowy.commandLineArgument.exception;

public class InvalidOptionValueException extends RuntimeException {
    public InvalidOptionValueException(final String message) {
        super(message);
    }
}
