package com.piaskowy.commandLineArgument.exception;

public class RequiredOptionMissingException extends RuntimeException {
    public RequiredOptionMissingException(final String message) {
        super(message);
    }
}
