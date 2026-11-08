package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.ComputerModel;

public interface UpdateComputerPort {
    ComputerModel update(ComputerModel computer);
}