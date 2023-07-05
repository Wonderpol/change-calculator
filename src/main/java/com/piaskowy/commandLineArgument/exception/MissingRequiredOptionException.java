package com.piaskowy.commandLineArgument.exception;

public class MissingRequiredOptionException extends RuntimeException {
    public MissingRequiredOptionException(final String message) {
        super(message);
    }
}
