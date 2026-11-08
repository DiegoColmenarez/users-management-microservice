package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.jcaa.usersmanagement.application.port.out.GetComputerByIdPort;
import com.jcaa.usersmanagement.application.port.out.UpdateComputerPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateComputerCommand;
import com.jcaa.usersmanagement.domain.enums.Category;
import com.jcaa.usersmanagement.domain.exception.ComputerNotFoundException;
import com.jcaa.usersmanagement.domain.model.ComputerModel;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("UpdateComputerService")
@ExtendWith(MockitoExtension.class)
class UpdateComputerServiceTest {

  @Mock private UpdateComputerPort updateComputerPort;
  @Mock private GetComputerByIdPort getComputerByIdPort;

  private UpdateComputerService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service =
          new UpdateComputerService(
              updateComputerPort, getComputerByIdPort, validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() actualiza el equipo cuando existe y el command es válido")
  void shouldUpdateComputerWhenFound() {
    final UpdateComputerCommand command = ComputerServiceTestFixtures.validUpdateCommand();
    when(getComputerByIdPort.getById(any()))
        .thenReturn(Optional.of(ComputerServiceTestFixtures.computer()));

    service.execute(command);

    final ArgumentCaptor<ComputerModel> computerCaptor =
        ArgumentCaptor.forClass(ComputerModel.class);
    verify(updateComputerPort).update(computerCaptor.capture());
    assertEquals(
        ComputerServiceTestFixtures.computer().getId(), computerCaptor.getValue().getId());
    assertEquals(Category.GAMING, computerCaptor.getValue().getComputerCategory());
  }

  @Test
  @DisplayName("execute() lanza ComputerNotFoundException cuando el equipo no existe")
  void shouldThrowWhenComputerDoesNotExist() {
    final UpdateComputerCommand command = ComputerServiceTestFixtures.validUpdateCommand();
    when(getComputerByIdPort.getById(any())).thenReturn(Optional.empty());

    assertThrows(ComputerNotFoundException.class, () -> service.execute(command));
    verify(updateComputerPort, never()).update(any());
  }

  @Test
  @DisplayName("execute() rechaza el command inválido sin consultar ni actualizar")
  void shouldRejectInvalidCommand() {
    final UpdateComputerCommand command =
        new UpdateComputerCommand(
            "",
            "GAMING",
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
    verifyNoInteractions(updateComputerPort, getComputerByIdPort);
  }
}
