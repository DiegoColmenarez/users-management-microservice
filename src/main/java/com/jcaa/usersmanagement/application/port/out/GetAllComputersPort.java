package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.ComputerModel;
import java.util.List;

public interface GetAllComputersPort {
    List<ComputerModel> getAll();
}
