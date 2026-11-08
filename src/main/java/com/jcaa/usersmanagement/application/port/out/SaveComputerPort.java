package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.ComputerModel;

public interface SaveComputerPort {
    ComputerModel save(ComputerModel computer);
}