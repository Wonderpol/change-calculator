package com.piaskowy.commandLineArgument.exception;

public class CanNotSetOptionValueException extends RuntimeException {
    public CanNotSetOptionValueException(final String message) {
        super(message);
    }
}
