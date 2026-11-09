package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.CreateComputerUseCase;
import com.jcaa.usersmanagement.application.port.out.SaveComputerPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateComputerCommand;
import com.jcaa.usersmanagement.application.service.mapper.ComputerApplicationMapper;
import com.jcaa.usersmanagement.domain.model.ComputerModel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import java.util.Set;

@RequiredArgsConstructor
public class CreateComputerService implements CreateComputerUseCase {

    private final SaveComputerPort saveComputerPort;
    private final Validator validator;

    @Override
    public void execute(final CreateComputerCommand command) {
        validateCommand(command);
        final ComputerModel computerToSave = ComputerApplicationMapper.fromCreateCommandToModel(command);
        saveComputerPort.save(computerToSave);
    }

    private void validateCommand(final CreateComputerCommand command) {
        final Set<ConstraintViolation<CreateComputerCommand>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}