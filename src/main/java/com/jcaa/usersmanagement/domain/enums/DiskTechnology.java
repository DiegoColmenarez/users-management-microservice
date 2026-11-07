package com.jcaa.usersmanagement.domain.enums;

public enum DiskTechnology {
    HDD,
    SSD,
    NVME;

    public static boolean isValidValue(final String value) {
        for (final DiskTechnology technology : values()) {
            if (technology.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    public static DiskTechnology fromValue(final String value) {
        for (final DiskTechnology technology : values()) {
            if (technology.name().equalsIgnoreCase(value)) {
                return technology;
            }
        }
        throw new IllegalArgumentException("Invalid DiskTechnology: " + value);
    }
}