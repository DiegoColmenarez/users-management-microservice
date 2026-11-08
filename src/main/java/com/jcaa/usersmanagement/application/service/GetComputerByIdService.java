package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetComputerByIdUseCase;
import com.jcaa.usersmanagement.application.port.out.GetComputerByIdPort;
import com.jcaa.usersmanagement.application.service.dto.query.GetComputerByIdQuery;
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
public class GetComputerByIdService implements GetComputerByIdUseCase {

    private final GetComputerByIdPort getComputerByIdPort;
    private final Validator validator;

    @Override
    public ComputerModel execute(final GetComputerByIdQuery query) {
        validateQuery(query);
        final ComputerId computerId = ComputerApplicationMapper.fromGetComputerByIdQueryToComputerId(query);
        return getComputerByIdPort
                .getById(computerId)
                .orElseThrow(() -> ComputerNotFoundException.becauseIdWasNotFound(computerId.value().toString()));
    }

    private void validateQuery(final GetComputerByIdQuery query) {
        final Set<ConstraintViolation<GetComputerByIdQuery>> violations = validator.validate(query);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}