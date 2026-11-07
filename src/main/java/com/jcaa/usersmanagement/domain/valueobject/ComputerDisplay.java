package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidComputerDisplayException;
import java.math.BigDecimal;

public record ComputerDisplay(String monitorBrand, BigDecimal inches) {

    private static void validateMonitorBrandNull(String monitorBrand) {
        if (monitorBrand == null) {
            throw InvalidComputerDisplayException.becauseBrandIsNull();
        }
    }

    private static void validateMonitorBrandEmpty(String monitorBrand) {
        if (monitorBrand.trim().isEmpty()) {
            throw InvalidComputerDisplayException.becauseBrandIsEmpty();
        }
    }

    private static void validateInchesNull(BigDecimal inches) {
        if (inches == null) {
            throw InvalidComputerDisplayException.becauseInchesAreNull();
        }
    }

    private static void validateInches(BigDecimal inches) {
        if (inches.compareTo(BigDecimal.ZERO) <= 0) {
            throw InvalidComputerDisplayException.becauseInchesAreInvalid();
        }
    }

    private static void validateInchesRange(BigDecimal inches) {
        if (inches.compareTo(new BigDecimal("80")) > 0) {
            throw InvalidComputerDisplayException.becauseInchesExceeded();
        }
    }

    public ComputerDisplay {
        validateMonitorBrandNull(monitorBrand);
        validateMonitorBrandEmpty(monitorBrand);
        validateInchesNull(inches);
        validateInches(inches);
        validateInchesRange(inches);
        monitorBrand = monitorBrand.trim().toUpperCase();
    }
}