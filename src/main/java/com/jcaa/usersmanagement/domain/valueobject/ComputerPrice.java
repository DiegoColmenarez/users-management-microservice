package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidComputerPriceException;
import java.math.BigDecimal;

public record ComputerPrice(BigDecimal value) {

    private static final BigDecimal MAX_PRICE = new BigDecimal("1000000");

    private static void validateNull(BigDecimal value) {
        if (value == null) {
            throw InvalidComputerPriceException.becausePriceIsNull();
        }
    }

    private static void validatePositive(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw InvalidComputerPriceException.becausePriceIsInvalid();
        }
    }

    private static void validateMaximumPrice(BigDecimal value) {
        if (value.compareTo(MAX_PRICE) > 0) {
            throw InvalidComputerPriceException.becausePriceExceeded();
        }
    }

    private static void validateScale(BigDecimal value) {
        if (value.scale() > 2) {
            throw InvalidComputerPriceException.becausePriceHasTooManyDecimals();
        }
    }

    public ComputerPrice {
        validateNull(value);
        validatePositive(value);
        validateMaximumPrice(value);
        validateScale(value);
    }
}