package com.jcaa.usersmanagement.domain.exception;

public class InvalidComputerProcessorException extends DomainException {
    public InvalidComputerProcessorException(String message) {
        super(message);
    }

    private static final String MESSAGE_BRAND_NULL = "Processor brand is invalid. Brand is Null";
    private static final String MESSAGE_BRAND_EMPTY = "Processor brand is invalid, it cannot be empty";
    private static final String MESSAGE_SPEED_NULL = "Processor speed is invalid. Speed is Null";
    private static final String MESSAGE_SPEED_EMPTY = "Processor speed is invalid, it cannot be empty";
    private static final String MESSAGE_SPEED_FORMAT_INVALID = "Processor speed format is invalid, expected format: 3.5GHz or 3500MHz";
    private static final String MESSAGE_SPEED_OUT_OF_RANGE = "Processor speed is out of range, must be between 0.1GHz and 10GHz";

    public static InvalidComputerProcessorException becauseBrandIsNull() {
        return new InvalidComputerProcessorException(MESSAGE_BRAND_NULL);
    }

    public static InvalidComputerProcessorException becauseBrandIsEmpty() {
        return new InvalidComputerProcessorException(MESSAGE_BRAND_EMPTY);
    }

    public static InvalidComputerProcessorException becauseSpeedIsNull() {
        return new InvalidComputerProcessorException(MESSAGE_SPEED_NULL);
    }

    public static InvalidComputerProcessorException becauseSpeedIsEmpty() {
        return new InvalidComputerProcessorException(MESSAGE_SPEED_EMPTY);
    }

    public static InvalidComputerProcessorException becauseSpeedFormatIsInvalid() {
        return new InvalidComputerProcessorException(MESSAGE_SPEED_FORMAT_INVALID);
    }

    public static InvalidComputerProcessorException becauseSpeedOutOfRange() {
        return new InvalidComputerProcessorException(MESSAGE_SPEED_OUT_OF_RANGE);
    }
}