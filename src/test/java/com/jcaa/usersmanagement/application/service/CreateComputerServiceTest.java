package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import com.jcaa.usersmanagement.application.port.out.SaveComputerPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateComputerCommand;
import com.jcaa.usersmanagement.domain.enums.Category;
import com.jcaa.usersmanagement.domain.model.ComputerModel;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("CreateComputerService")
@ExtendWith(MockitoExtension.class)
class CreateComputerServiceTest {

  @Mock private SaveComputerPort saveComputerPort;

  private CreateComputerService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service = new CreateComputerService(saveComputerPort, validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() mapea y guarda el equipo cuando el command es válido")
  void shouldSaveComputerWhenCommandIsValid() {
    final CreateComputerCommand command = ComputerServiceTestFixtures.validCreateCommand();

    service.execute(command);

    final ArgumentCaptor<ComputerModel> computerCaptor =
        ArgumentCaptor.forClass(ComputerModel.class);
    verify(saveComputerPort).save(computerCaptor.capture());
    assertNotNull(computerCaptor.getValue().getId());
    assertEquals(Category.GAMING, computerCaptor.getValue().getComputerCategory());
  }

  @Test
  @DisplayName("execute() rechaza el command inválido sin guardar")
  void shouldRejectInvalidCommand() {
    final CreateComputerCommand command =
        new CreateComputerCommand(
            "",
            "Lenovo",
            "Intel",
            "3.5GHz",
            "DDR5",
            "16GB",
            "SSD",
            "1TB",
            "Dell",
            null,
            4,
            1,
            null);

    assertThrows(ConstraintViolationException.class, () -> service.execute(command));
    verifyNoInteractions(saveComputerPort);
  }
}
