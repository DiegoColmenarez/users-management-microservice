package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jcaa.usersmanagement.application.port.out.GetAllComputersPort;
import com.jcaa.usersmanagement.domain.model.ComputerModel;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("GetAllComputersService")
@ExtendWith(MockitoExtension.class)
class GetAllComputersServiceTest {

  @Mock private GetAllComputersPort getAllComputersPort;

  private GetAllComputersService service;

  @BeforeEach
  void setUp() {
    service = new GetAllComputersService(getAllComputersPort);
  }

  @Test
  @DisplayName("execute() retorna los equipos proporcionados por el puerto")
  void shouldReturnComputersFromPort() {
    final ComputerModel computer = ComputerServiceTestFixtures.computer();
    final List<ComputerModel> computers = List.of(computer);
    when(getAllComputersPort.getAll()).thenReturn(computers);

    final List<ComputerModel> result = service.execute();

    assertEquals(1, result.size());
    assertSame(computer, result.get(0));
    assertSame(computers, result);
    verify(getAllComputersPort).getAll();
  }

  @Test
  @DisplayName("execute() retorna una lista vacía cuando no hay equipos")
  void shouldReturnEmptyListWhenNoComputersExist() {
    when(getAllComputersPort.getAll()).thenReturn(List.of());

    final List<ComputerModel> result = service.execute();

    assertTrue(result.isEmpty());
    verify(getAllComputersPort).getAll();
  }
}
