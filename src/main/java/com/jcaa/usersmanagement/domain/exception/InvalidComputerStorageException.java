package com.jcaa.usersmanagement.domain.exception;

public class InvalidComputerStorageException extends DomainException {
    public InvalidComputerStorageException(String message) {
        super(message);
    }

    private static final String MESSAGE_TECHNOLOGY_INVALID = "Storage technology is invalid";
    private static final String MESSAGE_CAPACITY_INVALID = "Storage capacity is invalid";
    private static final String MESSAGE_CAPACITY_INVALID2 = "Storage capacity is invalid. Capacity is Null";
    private static final String MESSAGE_CAPACITY_FORMAT_INVALID = "Storage capacity format is invalid, expected format: MB, GB, TB";

    public static InvalidComputerStorageException becauseTechnologyIsInvalid() {
        return new InvalidComputerStorageException(MESSAGE_TECHNOLOGY_INVALID);
    }

    public static InvalidComputerStorageException becauseCapacityIsInvalid() {
        return new InvalidComputerStorageException(MESSAGE_CAPACITY_INVALID);
    }
    public static InvalidComputerStorageException becauseCapacityIsNull() {
        return new InvalidComputerStorageException(MESSAGE_CAPACITY_INVALID2);
    }

    public static InvalidComputerStorageException becauseTechnologyIsNull() {
        return new InvalidComputerStorageException(MESSAGE_CAPACITY_INVALID2);
    }

    public static InvalidComputerStorageException becauseCapacityFormatIsInvalid() {
        return new InvalidComputerStorageException(MESSAGE_CAPACITY_FORMAT_INVALID);
    }
}