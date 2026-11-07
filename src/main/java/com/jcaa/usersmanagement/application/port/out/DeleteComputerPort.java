package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.valueobject.ComputerId;

public interface DeleteComputerPort {
    void delete(ComputerId computerId);
}
