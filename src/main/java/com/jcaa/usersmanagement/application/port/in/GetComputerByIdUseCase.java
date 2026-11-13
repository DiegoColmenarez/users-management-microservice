package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.query.GetComputerByIdQuery;
import com.jcaa.usersmanagement.domain.model.ComputerModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface GetComputerByIdUseCase {
    ComputerModel execute(@NotNull @Valid GetComputerByIdQuery query);
}