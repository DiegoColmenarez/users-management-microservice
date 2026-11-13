package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ComputerRestRequest(
        @NotBlank(message = "category must not be blank") String category,
        @NotBlank(message = "brand must not be blank") String brand,
        @NotBlank(message = "cpuBrand must not be blank") String cpuBrand,
        @NotBlank(message = "cpuSpeed must not be blank") String cpuSpeed,
        @NotBlank(message = "ramTechnology must not be blank") String ramTechnology,
        @NotBlank(message = "ramCapacity must not be blank") String ramCapacity,
        @NotBlank(message = "diskTechnology must not be blank") String diskTechnology,
        @NotBlank(message = "diskCapacity must not be blank") String diskCapacity,
        @NotBlank(message = "monitorBrand must not be blank") String monitorBrand,
        @NotNull(message = "monitorInches must not be null") BigDecimal monitorInches,
        @NotNull(message = "usbPorts must not be null") Integer usbPorts,
        @NotNull(message = "hdmiPorts must not be null") Integer hdmiPorts,
        @NotNull(message = "price must not be null") BigDecimal price) {}