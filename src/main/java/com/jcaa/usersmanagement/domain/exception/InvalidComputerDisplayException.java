package com.jcaa.usersmanagement.domain.exception;

public class InvalidComputerDisplayException extends DomainException {
    public InvalidComputerDisplayException(String message) {
        super(message);
    }

    private static final String MESSAGE_BRAND_NULL = "Monitor brand is invalid. Brand is Null";
    private static final String MESSAGE_BRAND_EMPTY = "Monitor brand is invalid, it cannot be empty";
    private static final String MESSAGE_INCHES_NULL = "Monitor inches are invalid. Inches are Null";
    private static final String MESSAGE_INCHES_INVALID = "Monitor inches are invalid, must be greater than 0";
    private static final String MESSAGE_INCHES_EXCEEDED = "Monitor inches exceeded, maximum is 80 inches";

    public static InvalidComputerDisplayException becauseBrandIsNull() {
        return new InvalidComputerDisplayException(MESSAGE_BRAND_NULL);
    }

    public static InvalidComputerDisplayException becauseBrandIsEmpty() {
        return new InvalidComputerDisplayException(MESSAGE_BRAND_EMPTY);
    }

    public static InvalidComputerDisplayException becauseInchesAreNull() {
        return new InvalidComputerDisplayException(MESSAGE_INCHES_NULL);
    }

    public static InvalidComputerDisplayException becauseInchesAreInvalid() {
        return new InvalidComputerDisplayException(MESSAGE_INCHES_INVALID);
    }

    public static InvalidComputerDisplayException becauseInchesExceeded() {
        return new InvalidComputerDisplayException(MESSAGE_INCHES_EXCEEDED);
    }
}
