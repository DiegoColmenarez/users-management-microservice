package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.service.dto.command.CreateComputerCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateComputerCommand;
import com.jcaa.usersmanagement.domain.enums.Category;
import com.jcaa.usersmanagement.domain.enums.DiskTechnology;
import com.jcaa.usersmanagement.domain.enums.RamTechnology;
import com.jcaa.usersmanagement.domain.model.ComputerModel;
import com.jcaa.usersmanagement.domain.valueobject.ComputerBrand;
import com.jcaa.usersmanagement.domain.valueobject.ComputerDisplay;
import com.jcaa.usersmanagement.domain.valueobject.ComputerId;
import com.jcaa.usersmanagement.domain.valueobject.ComputerMemory;
import com.jcaa.usersmanagement.domain.valueobject.ComputerPorts;
import com.jcaa.usersmanagement.domain.valueobject.ComputerPrice;
import com.jcaa.usersmanagement.domain.valueobject.ComputerProcessor;
import com.jcaa.usersmanagement.domain.valueobject.ComputerStorage;
import java.math.BigDecimal;

final class ComputerServiceTestFixtures {

  static final String COMPUTER_ID = "c50a191e-1fb6-47c7-bbe7-1af8f6dd28e3";

  private ComputerServiceTestFixtures() {}

  static CreateComputerCommand validCreateCommand() {
    return new CreateComputerCommand(
        "GAMING",
        "Lenovo",
        "Intel",
        "3.5GHz",
        "DDR5",
        "16GB",
        "SSD",
        "1TB",
        "Dell",
        new BigDecimal("27"),
        4,
        1,
        new BigDecimal("1299.99"));
  }

  static UpdateComputerCommand validUpdateCommand() {
    return new UpdateComputerCommand(
        COMPUTER_ID,
        "GAMING",
        "Lenovo",
        "Intel",
        "3.5GHz",
        "DDR5",
        "16GB",
        "SSD",
        "1TB",
        "Dell",
        new BigDecimal("27"),
        4,
        1,
        new BigDecimal("1299.99"));
  }

  static ComputerModel computer() {
    return new ComputerModel(
        ComputerId.fromString(COMPUTER_ID),
        Category.GAMING,
        new ComputerBrand("Lenovo"),
        new ComputerProcessor("Intel", "3.5GHz"),
        new ComputerMemory(RamTechnology.DDR5, "16GB"),
        new ComputerStorage(DiskTechnology.SSD, "1TB"),
        new ComputerDisplay("Dell", new BigDecimal("27")),
        new ComputerPorts(4, 1),
        new ComputerPrice(new BigDecimal("1299.99")));
  }
}
