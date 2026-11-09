package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.jcaa.usersmanagement.application.port.out.GetComputerByIdPort;
import com.jcaa.usersmanagement.application.service.dto.query.GetComputerByIdQuery;
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
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("GetComputerByIdService")
@ExtendWith(MockitoExtension.class)
class GetComputerByIdServiceTest {

  @Mock private GetComputerByIdPort getComputerByIdPort;

  private GetComputerByIdService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service = new GetComputerByIdService(getComputerByIdPort, validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() retorna el equipo cuando existe")
  void shouldReturnComputerWhenFound() {
    final GetComputerByIdQuery query =
        new GetComputerByIdQuery(ComputerServiceTestFixtures.COMPUTER_ID);
    final ComputerModel computer = ComputerServiceTestFixtures.computer();
    when(getComputerByIdPort.getById(any())).thenReturn(Optional.of(computer));

    final ComputerModel result = service.execute(query);

    assertSame(computer, result);
    verify(getComputerByIdPort).getById(computer.getId());
  }

  @Test
  @DisplayName("execute() lanza ComputerNotFoundException cuando el equipo no existe")
  void shouldThrowWhenComputerDoesNotExist() {
    final GetComputerByIdQuery query =
        new GetComputerByIdQuery(ComputerServiceTestFixtures.COMPUTER_ID);
    when(getComputerByIdPort.getById(any())).thenReturn(Optional.empty());

    assertThrows(ComputerNotFoundException.class, () -> service.execute(query));
    verify(getComputerByIdPort).getById(ComputerServiceTestFixtures.computer().getId());
  }

  @Test
  @DisplayName("execute() rechaza el query inválido sin consultar el puerto")
  void shouldRejectInvalidQuery() {
    final GetComputerByIdQuery query = new GetComputerByIdQuery(" ");

    assertThrows(ConstraintViolationException.class, () -> service.execute(query));
    verifyNoInteractions(getComputerByIdPort);
  }
}
