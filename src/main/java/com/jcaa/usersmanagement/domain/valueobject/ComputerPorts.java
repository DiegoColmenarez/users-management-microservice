package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidComputerPortsException;

public record ComputerPorts(Integer usbPorts, Integer hdmiPorts) {

    private static void validateUsbPortsNull(Integer usbPorts) {
        if (usbPorts == null) {
            throw InvalidComputerPortsException.becauseUsbPortsAreNull();
        }
    }

    private static void validateHdmiPortsNull(Integer hdmiPorts) {
        if (hdmiPorts == null) {
            throw InvalidComputerPortsException.becauseHdmiPortsAreNull();
        }
    }

    private static void validateUsbPorts(Integer usbPorts) {
        if (usbPorts < 0) {
            throw InvalidComputerPortsException.becauseUsbPortsAreInvalid();
        }
    }

    private static void validateHdmiPorts(Integer hdmiPorts) {
        if (hdmiPorts < 0) {
            throw InvalidComputerPortsException.becauseHdmiPortsAreInvalid();
        }
    }

    private static void validateTotalPorts(Integer usbPorts, Integer hdmiPorts) {
        if (usbPorts + hdmiPorts > 20) {
            throw InvalidComputerPortsException.becauseTotalPortsExceeded();
        }
    }

    public ComputerPorts {
        validateUsbPortsNull(usbPorts);
        validateHdmiPortsNull(hdmiPorts);
        validateUsbPorts(usbPorts);
        validateHdmiPorts(hdmiPorts);
        validateTotalPorts(usbPorts, hdmiPorts);
    }
}