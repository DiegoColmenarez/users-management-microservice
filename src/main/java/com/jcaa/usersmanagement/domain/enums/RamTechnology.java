package com.jcaa.usersmanagement.domain.enums;

import com.jcaa.usersmanagement.domain.exception.InvalidComputerMemoryException;

public enum RamTechnology {
    DDR3("DDR3"),
    DDR4("DDR4"),
    DDR5("DDR5"),
    DRAM("DRAM");

    private final String value;

    RamTechnology(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean isValidValue(String value) {
        for (RamTechnology technology : RamTechnology.values()) {
            if (technology.value.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    public static RamTechnology fromValue(String value) {
        for (RamTechnology technology : RamTechnology.values()) {
            if (technology.value.equalsIgnoreCase(value)) {
                return technology;
            }
        }
        throw InvalidComputerMemoryException.becauseTechnologyIsInvalid();
    }
}