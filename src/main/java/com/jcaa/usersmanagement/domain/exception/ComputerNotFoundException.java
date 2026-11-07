package com.jcaa.usersmanagement.domain.exception;

public class ComputerNotFoundException extends RuntimeException {
    public ComputerNotFoundException(String message) {
        super(message);
    }

    private static final String MESSAGE_BY_ID = "The computer with id '%s' was not found.";

    public static ComputerNotFoundException becauseIdWasNotFound(final String computerId) {
        return new ComputerNotFoundException(String.format(MESSAGE_BY_ID, computerId));
    }
}
