package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.ComputerModel;

public interface GetComputerByIdUseCase {
    ComputerModel execute();
}