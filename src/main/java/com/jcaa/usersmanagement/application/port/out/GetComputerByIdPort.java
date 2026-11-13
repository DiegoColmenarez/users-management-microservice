package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.ComputerModel;
import com.jcaa.usersmanagement.domain.valueobject.ComputerId;
import java.util.Optional;

public interface GetComputerByIdPort {
    Optional<ComputerModel> getById(ComputerId computerId);
}
