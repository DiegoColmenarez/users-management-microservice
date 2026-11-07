package com.jcaa.usersmanagement.domain.exception;

public class InvalidComputerBrandException extends DomainException {
    public InvalidComputerBrandException(String message) {
        super(message);
    }

    private static final String MESSAGE_BRAND_NULL = "Brand is invalid. Brand is Null";
    private static final String MESSAGE_BRAND_EMPTY = "Brand is invalid, it cannot be empty";
    private static final String MESSAGE_FORMAT_INVALID = "Brand format is invalid, only letters, numbers, spaces, hyphens and underscores are allowed";
    private static final String MESSAGE_LENGTH_INVALID = "Brand is invalid, length must be between 2 and 50 characters";

    public static InvalidComputerBrandException becauseBrandIsNull() {
        return new InvalidComputerBrandException(MESSAGE_BRAND_NULL);
    }

    public static InvalidComputerBrandException becauseBrandIsEmpty() {
        return new InvalidComputerBrandException(MESSAGE_BRAND_EMPTY);
    }

    public static InvalidComputerBrandException becauseFormatIsInvalid() {
        return new InvalidComputerBrandException(MESSAGE_FORMAT_INVALID);
    }

    public static InvalidComputerBrandException becauseLengthIsInvalid() {
        return new InvalidComputerBrandException(MESSAGE_LENGTH_INVALID);
    }
}