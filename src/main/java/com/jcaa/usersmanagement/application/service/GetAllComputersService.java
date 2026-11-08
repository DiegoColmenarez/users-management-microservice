package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetAllComputersUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAllComputersPort;
import com.jcaa.usersmanagement.domain.model.ComputerModel;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class GetAllComputersService implements GetAllComputersUseCase {

    private final GetAllComputersPort getAllComputersPort;

    @Override
    public List<ComputerModel> execute() {
        return getAllComputersPort.getAll();
    }
}