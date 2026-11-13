package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response;

import java.math.BigDecimal;

public record ComputerRestResponse(
        String id, String category, String brand,
        String cpuBrand, String cpuSpeed,
        String ramTechnology, String ramCapacity,
        String diskTechnology, String diskCapacity,
        String monitorBrand, BigDecimal monitorInches,
        Integer usbPorts, Integer hdmiPorts, BigDecimal price) {}