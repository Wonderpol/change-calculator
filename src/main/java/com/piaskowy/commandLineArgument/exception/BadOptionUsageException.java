package com.piaskowy.commandLineArgument.exception;

public class BadOptionUsageException extends RuntimeException {
    public BadOptionUsageException(final String message) {
        super(message);
    }
}
