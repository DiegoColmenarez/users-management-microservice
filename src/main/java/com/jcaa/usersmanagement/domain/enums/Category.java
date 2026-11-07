package com.jcaa.usersmanagement.domain.enums;

import com.jcaa.usersmanagement.domain.exception.InvalidComputerCategoryException;

public enum Category {
    GAMING("GAMING"),
    OFFICE("OFFICE"),
    WORKSTATION("WORKSTATION"),
    ULTRABOOK("ULTRABOOK"),
    ALL_IN_ONE("ALL_IN_ONE");

    private final String value;

    Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean isValidValue(String value) {
        for (Category category : Category.values()) {
            if (category.value.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    public static Category fromValue(String value) {
        for (Category category : Category.values()) {
            if (category.value.equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw InvalidComputerCategoryException.becauseCategoryIsInvalid();
    }
}