package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidComputerBrandException;

public record ComputerBrand(String value) {

    private static void validateNull(String value) {
        if (value == null) {
            throw InvalidComputerBrandException.becauseBrandIsNull();
        }
    }

    private static void validateEmpty(String value) {
        if (value.trim().isEmpty()) {
            throw InvalidComputerBrandException.becauseBrandIsEmpty();
        }
    }

    private static void validateFormat(String value) {
        if (!value.matches("^[a-zA-Z0-9\\s\\-_]+$")) {
            throw InvalidComputerBrandException.becauseFormatIsInvalid();
        }
    }

    private static void validateLength(String value) {
        if (value.length() < 2 || value.length() > 50) {
            throw InvalidComputerBrandException.becauseLengthIsInvalid();
        }
    }

    public ComputerBrand {
        validateNull(value);
        validateEmpty(value);
        validateFormat(value.trim());
        validateLength(value.trim());
        value = value.trim().toUpperCase();
    }
}