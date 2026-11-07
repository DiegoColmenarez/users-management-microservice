package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.enums.DiskTechnology;
import com.jcaa.usersmanagement.domain.exception.InvalidComputerStorageException;

public record ComputerStorage(DiskTechnology diskTechnology, String diskCapacity) {

    private static void validateDiskTechnologyEmpty(String diskTechnology) {
        if (diskTechnology.trim().isEmpty()) {
            throw InvalidComputerStorageException.becauseTechnologyIsInvalid();
        }
    }
    private static void validateDiskCapacityEmpty(String diskCapacity) {
        if (diskCapacity.trim().isEmpty()) {
            throw InvalidComputerStorageException.becauseCapacityIsInvalid();
        }
    }
    private static void validateDiskCapacityNull(String diskCapacity) {
        if (diskCapacity == null) {
            throw InvalidComputerStorageException.becauseCapacityIsNull();
        }
    }

    private static void validateTechnologyNull(DiskTechnology diskTechnology) {
        if (diskTechnology == null) {
            throw InvalidComputerStorageException.becauseTechnologyIsNull();
        }
    }

    private static void validateCapacityFormat(String diskCapacity) {
        if (!diskCapacity.matches("^\\d+\\s*(GB|TB|MB)$")) {
            throw InvalidComputerStorageException.becauseCapacityFormatIsInvalid();
        }
    }
    private static void validateDiskTechnologyFormat(String diskTechnology) {
        if (!DiskTechnology.isValidValue(diskTechnology)) {
            throw InvalidComputerStorageException.becauseCapacityFormatIsInvalid();
        }
    }

    public ComputerStorage {
        validateDiskCapacityNull(diskCapacity);
        validateDiskCapacityEmpty(diskCapacity);
        validateCapacityFormat(diskCapacity.trim());
        validateTechnologyNull(diskTechnology);
        validateDiskTechnologyEmpty(String.valueOf(diskTechnology));
        validateDiskTechnologyFormat(String.valueOf(diskTechnology));
        diskCapacity = diskCapacity.trim().toUpperCase();
    }
}