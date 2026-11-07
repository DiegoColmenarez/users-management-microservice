package com.jcaa.usersmanagement.domain.exception;

public class InvalidComputerMemoryException extends DomainException {
    public InvalidComputerMemoryException(String message) {
        super(message);
    }

    private static final String MESSAGE_TECHNOLOGY_NULL = "RAM technology is invalid. Technology is Null";
    private static final String MESSAGE_CAPACITY_NULL = "RAM capacity is invalid. Capacity is Null";
    private static final String MESSAGE_CAPACITY_EMPTY = "RAM capacity is invalid, it cannot be empty";
    private static final String MESSAGE_CAPACITY_FORMAT_INVALID = "RAM capacity format is invalid, expected format: 16GB";

    public static InvalidComputerMemoryException becauseTechnologyIsNull() {
        return new InvalidComputerMemoryException(MESSAGE_TECHNOLOGY_NULL);
    }

    public static InvalidComputerMemoryException becauseCapacityIsNull() {
        return new InvalidComputerMemoryException(MESSAGE_CAPACITY_NULL);
    }

    public static InvalidComputerMemoryException becauseCapacityIsEmpty() {
        return new InvalidComputerMemoryException(MESSAGE_CAPACITY_EMPTY);
    }

    public static InvalidComputerMemoryException becauseCapacityFormatIsInvalid() {
        return new InvalidComputerMemoryException(MESSAGE_CAPACITY_FORMAT_INVALID);
    }
}