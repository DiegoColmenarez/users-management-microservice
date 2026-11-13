package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidComputerProcessorException;

public record ComputerProcessor(String cpuBrand, String cpuSpeed) {

    private static final String SPEED_PATTERN = "(?i)^\\d+(\\.\\d+)?\\s*(GHz|MHz)?$";

    private static final double MIN_SPEED_GHZ = 0.1;
    private static final double MAX_SPEED_GHZ = 10.0;

    private static void validateCpuBrandNull(String cpuBrand) {
        if (cpuBrand == null) {
            throw InvalidComputerProcessorException.becauseBrandIsNull();
        }
    }

    private static void validateCpuBrandEmpty(String cpuBrand) {
        if (cpuBrand.trim().isEmpty()) {
            throw InvalidComputerProcessorException.becauseBrandIsEmpty();
        }
    }

    private static void validateCpuSpeedNull(String cpuSpeed) {
        if (cpuSpeed == null) {
            throw InvalidComputerProcessorException.becauseSpeedIsNull();
        }
    }

    private static void validateCpuSpeedEmpty(String cpuSpeed) {
        if (cpuSpeed.trim().isEmpty()) {
            throw InvalidComputerProcessorException.becauseSpeedIsEmpty();
        }
    }

    private static void validateSpeedFormat(String cpuSpeed) {
        if (!cpuSpeed.matches(SPEED_PATTERN)) {
            throw InvalidComputerProcessorException.becauseSpeedFormatIsInvalid();
        }
    }

    private static void validateSpeedRange(String cpuSpeed) {
        double speedInGhz = toGhz(cpuSpeed);
        if (speedInGhz < MIN_SPEED_GHZ || speedInGhz > MAX_SPEED_GHZ) {
            throw InvalidComputerProcessorException.becauseSpeedOutOfRange();
        }
    }

    private static double toGhz(String cpuSpeed) {
        String normalized = cpuSpeed.trim().toUpperCase();
        if (normalized.endsWith("MHZ")) {
            String number = normalized.replace("MHZ", "").trim();
            return Double.parseDouble(number) / 1000.0;
        }
        if (normalized.endsWith("GHZ")) {
            String number = normalized.replace("GHZ", "").trim();
            return Double.parseDouble(number);
        }
        return Double.parseDouble(normalized);
    }

    private static String normalizeSpeed(String cpuSpeed) {
        String trimmed = cpuSpeed.trim().toUpperCase();
        if (trimmed.matches("^\\d+(\\.\\d+)?$")) {
            return trimmed + "GHZ";
        }
        return trimmed;
    }

    public ComputerProcessor {
        validateCpuBrandNull(cpuBrand);
        validateCpuBrandEmpty(cpuBrand);
        validateCpuSpeedNull(cpuSpeed);
        validateCpuSpeedEmpty(cpuSpeed);
        validateSpeedFormat(cpuSpeed.trim());
        validateSpeedRange(cpuSpeed.trim());
        cpuBrand = cpuBrand.trim().toUpperCase();
        cpuSpeed = normalizeSpeed(cpuSpeed);
    }
}