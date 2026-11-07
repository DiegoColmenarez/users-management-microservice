package com.jcaa.usersmanagement.domain.exception;

public class InvalidComputerPortsException extends DomainException {
    public InvalidComputerPortsException(String message) {
        super(message);
    }

    private static final String MESSAGE_USB_NULL = "USB ports are invalid. USB ports are Null";
    private static final String MESSAGE_HDMI_NULL = "HDMI ports are invalid. HDMI ports are Null";
    private static final String MESSAGE_USB_INVALID = "USB ports are invalid, cannot be negative";
    private static final String MESSAGE_HDMI_INVALID = "HDMI ports are invalid, cannot be negative";
    private static final String MESSAGE_TOTAL_EXCEEDED = "Total ports exceeded, maximum is 20 ports";

    public static InvalidComputerPortsException becauseUsbPortsAreNull() {
        return new InvalidComputerPortsException(MESSAGE_USB_NULL);
    }

    public static InvalidComputerPortsException becauseHdmiPortsAreNull() {
        return new InvalidComputerPortsException(MESSAGE_HDMI_NULL);
    }

    public static InvalidComputerPortsException becauseUsbPortsAreInvalid() {
        return new InvalidComputerPortsException(MESSAGE_USB_INVALID);
    }

    public static InvalidComputerPortsException becauseHdmiPortsAreInvalid() {
        return new InvalidComputerPortsException(MESSAGE_HDMI_INVALID);
    }

    public static InvalidComputerPortsException becauseTotalPortsExceeded() {
        return new InvalidComputerPortsException(MESSAGE_TOTAL_EXCEEDED);
    }
}