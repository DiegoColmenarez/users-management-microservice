package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.ComputerModel;
import java.util.List;

public interface GetAllComputersUseCase {
    List<ComputerModel> execute();
}
