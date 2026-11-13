package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.DeleteComputerUseCase;
import com.jcaa.usersmanagement.application.port.out.DeleteComputerPort;
import com.jcaa.usersmanagement.application.port.out.GetComputerByIdPort;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteComputerCommand;
import com.jcaa.usersmanagement.application.service.mapper.ComputerApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.ComputerNotFoundException;
import com.jcaa.usersmanagement.domain.valueobject.ComputerId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import java.util.Set;

@RequiredArgsConstructor
public class DeleteComputerService implements DeleteComputerUseCase {

    private final DeleteComputerPort deleteComputerPort;
    private final GetComputerByIdPort getComputerByIdPort;
    private final Validator validator;

    @Override
    public void execute(final DeleteComputerCommand command) {
        validateCommand(command);
        final ComputerId computerId = ComputerApplicationMapper.fromDeleteCommandToComputerId(command);
        ensureComputerExists(computerId);
        deleteComputerPort.delete(computerId);
    }

    private void validateCommand(final DeleteComputerCommand command) {
        final Set<ConstraintViolation<DeleteComputerCommand>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private void ensureComputerExists(final ComputerId computerId) {
        getComputerByIdPort
                .getById(computerId)
                .orElseThrow(() -> ComputerNotFoundException.becauseIdWasNotFound(computerId.value().toString()));
    }
}