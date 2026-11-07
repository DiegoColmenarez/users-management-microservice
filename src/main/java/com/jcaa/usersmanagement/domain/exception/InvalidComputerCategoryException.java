package com.jcaa.usersmanagement.domain.exception;

public class InvalidComputerCategoryException extends DomainException {
    public InvalidComputerCategoryException(String message) {
        super(message);
    }

    private static final String MESSAGE_CATEGORY_NULL = "Category is invalid. Category is Null";
    private static final String MESSAGE_CATEGORY_INVALID = "Category is invalid, it does not exist";

    public static InvalidComputerCategoryException becauseCategoryIsNull() {
        return new InvalidComputerCategoryException(MESSAGE_CATEGORY_NULL);
    }

    public static InvalidComputerCategoryException becauseCategoryIsInvalid() {
        return new InvalidComputerCategoryException(MESSAGE_CATEGORY_INVALID);
    }
}