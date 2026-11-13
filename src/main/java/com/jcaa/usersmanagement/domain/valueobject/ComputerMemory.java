package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.enums.RamTechnology;
import com.jcaa.usersmanagement.domain.exception.InvalidComputerMemoryException;

public record ComputerMemory(RamTechnology ramTechnology, String ramCapacity) {

    private static void validateRamTechnologyNull(RamTechnology ramTechnology) {
        if (ramTechnology == null) {
            throw InvalidComputerMemoryException.becauseTechnologyIsNull();
        }
    }

    private static void validateRamCapacityNull(String ramCapacity) {
        if (ramCapacity == null) {
            throw InvalidComputerMemoryException.becauseCapacityIsNull();
        }
    }

    private static void validateRamCapacityEmpty(String ramCapacity) {
        if (ramCapacity.trim().isEmpty()) {
            throw InvalidComputerMemoryException.becauseCapacityIsEmpty();
        }
    }

    private static void validateCapacityFormat(String ramCapacity) {
        if (!ramCapacity.matches("^\\d+\\s*(GB|MB)$")) {
            throw InvalidComputerMemoryException.becauseCapacityFormatIsInvalid();
        }
    }

    private static String normalizeCapacity(String ramCapacity) {
        return ramCapacity.replaceAll("\\s+", "").toUpperCase();
    }

    public ComputerMemory {
        validateRamTechnologyNull(ramTechnology);
        validateRamCapacityNull(ramCapacity);
        validateRamCapacityEmpty(ramCapacity);
        String trimmedCapacity = ramCapacity.trim();
        validateCapacityFormat(trimmedCapacity);
        ramCapacity = normalizeCapacity(trimmedCapacity);
    }
}