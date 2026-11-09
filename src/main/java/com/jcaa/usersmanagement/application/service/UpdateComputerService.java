package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.UpdateComputerUseCase;
import com.jcaa.usersmanagement.application.port.out.GetComputerByIdPort;
import com.jcaa.usersmanagement.application.port.out.UpdateComputerPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateComputerCommand;
import com.jcaa.usersmanagement.application.service.mapper.ComputerApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.ComputerNotFoundException;
import com.jcaa.usersmanagement.domain.model.ComputerModel;
import com.jcaa.usersmanagement.domain.valueobject.ComputerId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import java.util.Set;

@RequiredArgsConstructor
public class UpdateComputerService implements UpdateComputerUseCase {

    private final UpdateComputerPort updateComputerPort;
    private final GetComputerByIdPort getComputerByIdPort;
    private final Validator validator;

    @Override
    public void execute(final UpdateComputerCommand command) {
        validateCommand(command);
        final ComputerId computerId = ComputerId.fromString(command.id());
        findExistingComputerOrFail(computerId);
        final ComputerModel computerToUpdate = ComputerApplicationMapper.fromUpdateCommandToModel(command);
        updateComputerPort.update(computerToUpdate);
    }

    private void validateCommand(final UpdateComputerCommand command) {
        final Set<ConstraintViolation<UpdateComputerCommand>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private void findExistingComputerOrFail(final ComputerId computerId) {
        getComputerByIdPort
                .getById(computerId)
                .orElseThrow(() -> ComputerNotFoundException.becauseIdWasNotFound(computerId.value().toString()));
    }
}
