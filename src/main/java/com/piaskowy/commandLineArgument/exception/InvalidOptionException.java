package com.piaskowy.commandLineArgument.exception;

public class InvalidOptionException extends RuntimeException {
    public InvalidOptionException(final String message) {
        super(message);
    }
}
