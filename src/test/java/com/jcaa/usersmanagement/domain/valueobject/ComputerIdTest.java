package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidComputerIdException;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ComputerId")
class ComputerIdTest {

  @Test
  @DisplayName("Debe crear un identificador desde un UUID válido")
  void shouldCreateIdFromValidUuid() {
    UUID value = UUID.randomUUID();

    ComputerId id = new ComputerId(value);

    assertEquals(value, id.value());
  }

  @Test
  @DisplayName("Debe rechazar un UUID null")
  void shouldRejectNullUuid() {
    assertThrows(InvalidComputerIdException.class, () -> new ComputerId(null));
  }

  @Test
  @DisplayName("generate debe crear un identificador")
  void shouldGenerateId() {
    ComputerId id = ComputerId.generate();

    assertNotNull(id.value());
  }

  @Test
  @DisplayName("fromString debe convertir un UUID válido")
  void shouldParseValidUuid() {
    UUID value = UUID.randomUUID();

    ComputerId id = ComputerId.fromString(value.toString());

    assertEquals(value, id.value());
  }

  @Test
  @DisplayName("fromString debe rechazar un valor null o inválido")
  void shouldRejectNullOrInvalidString() {
    assertAll(
        () -> assertThrows(InvalidComputerIdException.class, () -> ComputerId.fromString(null)),
        () -> assertThrows(InvalidComputerIdException.class, () -> ComputerId.fromString("not-a-uuid")));
  }
}
