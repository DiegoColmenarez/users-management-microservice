package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.CreateComputerCommand;
import com.jcaa.usersmanagement.domain.valueobject.ComputerId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface CreateComputerUseCase {
    ComputerId execute(@NotNull @Valid CreateComputerCommand command);
}
