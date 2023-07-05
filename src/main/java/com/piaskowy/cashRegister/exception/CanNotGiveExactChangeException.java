package com.piaskowy.cashRegister.exception;

public class CanNotGiveExactChangeException extends RuntimeException {

    public CanNotGiveExactChangeException(final String message) {
        super(message);
    }
}
