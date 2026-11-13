package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidComputerIdException;
import java.util.UUID;

public record ComputerId(UUID value) {

    private static void validateNull(UUID value) {
        if (value == null) {
            throw InvalidComputerIdException.becauseIdIsNull();
        }
    }

    public ComputerId {
        validateNull(value);
    }

    public static ComputerId generate() {
        return new ComputerId(UUID.randomUUID());
    }

    public static ComputerId fromString(String id) {
        if (id == null) {
            throw InvalidComputerIdException.becauseIdIsNull();
        }
        try {
            return new ComputerId(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            throw InvalidComputerIdException.becauseIdIsInvalid();
        }
    }
}