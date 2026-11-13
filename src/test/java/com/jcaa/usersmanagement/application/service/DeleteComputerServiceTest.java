package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.jcaa.usersmanagement.application.port.out.DeleteComputerPort;
import com.jcaa.usersmanagement.application.port.out.GetComputerByIdPort;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteComputerCommand;
import com.jcaa.usersmanagement.domain.exception.ComputerNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("DeleteComputerService")
@ExtendWith(MockitoExtension.class)
class DeleteComputerServiceTest {

  @Mock private DeleteComputerPort deleteComputerPort;
  @Mock private GetComputerByIdPort getComputerByIdPort;

  private DeleteComputerService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service =
          new DeleteComputerService(
              deleteComputerPort, getComputerByIdPort, validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() elimina el equipo cuando existe")
  void shouldDeleteComputerWhenFound() {
    final DeleteComputerCommand command =
        new DeleteComputerCommand(ComputerServiceTestFixtures.COMPUTER_ID);
    when(getComputerByIdPort.getById(any()))
        .thenReturn(Optional.of(ComputerServiceTestFixtures.computer()));

    service.execute(command);

    verify(getComputerByIdPort).getById(ComputerServiceTestFixtures.computer().getId());
    verify(deleteComputerPort).delete(ComputerServiceTestFixtures.computer().getId());
  }

  @Test
  @DisplayName("execute() lanza ComputerNotFoundException cuando el equipo no existe")
  void shouldThrowWhenComputerDoesNotExist() {
    final DeleteComputerCommand command =
        new DeleteComputerCommand(ComputerServiceTestFixtures.COMPUTER_ID);
    when(getComputerByIdPort.getById(any())).thenReturn(Optional.empty());

    assertThrows(ComputerNotFoundException.class, () -> service.execute(command));
    verify(deleteComputerPort, never()).delete(any());
  }

  @Test
  @DisplayName("execute() rechaza el command inválido sin consultar el puerto")
  void shouldRejectInvalidCommand() {
    final DeleteComputerCommand command = new DeleteComputerCommand(" ");

    assertThrows(ConstraintViolationException.class, () -> service.execute(command));
    verifyNoInteractions(deleteComputerPort, getComputerByIdPort);
  }
}
