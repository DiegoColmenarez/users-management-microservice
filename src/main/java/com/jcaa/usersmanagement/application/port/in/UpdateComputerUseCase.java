package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.UpdateComputerCommand;
import com.jcaa.usersmanagement.domain.model.ComputerModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface UpdateComputerUseCase {
    void execute(@NotNull @Valid UpdateComputerCommand command);
}
