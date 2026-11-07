package com.jcaa.usersmanagement.domain.exception;

public class InvalidComputerPriceException extends DomainException {
    public InvalidComputerPriceException(String message) {
        super(message);
    }

    private static final String MESSAGE_PRICE_NULL = "Computer price is invalid. Price is Null";
    private static final String MESSAGE_PRICE_INVALID = "Computer price is invalid, must be greater than 0";
    private static final String MESSAGE_PRICE_EXCEEDED = "Computer price exceeded, maximum is $1,000,000";
    private static final String MESSAGE_PRICE_DECIMALS = "Computer price has too many decimals, maximum 2 decimal places";

    public static InvalidComputerPriceException becausePriceIsNull() {
        return new InvalidComputerPriceException(MESSAGE_PRICE_NULL);
    }

    public static InvalidComputerPriceException becausePriceIsInvalid() {
        return new InvalidComputerPriceException(MESSAGE_PRICE_INVALID);
    }

    public static InvalidComputerPriceException becausePriceExceeded() {
        return new InvalidComputerPriceException(MESSAGE_PRICE_EXCEEDED);
    }

    public static InvalidComputerPriceException becausePriceHasTooManyDecimals() {
        return new InvalidComputerPriceException(MESSAGE_PRICE_DECIMALS);
    }
}