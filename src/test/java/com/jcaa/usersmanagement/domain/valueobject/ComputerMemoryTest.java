package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.enums.RamTechnology;
import com.jcaa.usersmanagement.domain.exception.InvalidComputerMemoryException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("ComputerMemory")
class ComputerMemoryTest {

  @Test
  @DisplayName("Debe eliminar espacios y normalizar la capacidad a mayúsculas")
  void shouldNormalizeCapacity() {
    ComputerMemory memory = new ComputerMemory(RamTechnology.DDR5, " 32 GB ");

    assertAll(
        () -> assertEquals(RamTechnology.DDR5, memory.ramTechnology()),
        () -> assertEquals("32GB", memory.ramCapacity()));
  }

  @Test
  @DisplayName("Debe rechazar tecnología o capacidad null")
  void shouldRejectNullTechnologyOrCapacity() {
    assertAll(
        () ->
            assertThrows(
                InvalidComputerMemoryException.class,
                () -> new ComputerMemory(null, "16GB")),
        () ->
            assertThrows(
                InvalidComputerMemoryException.class,
                () -> new ComputerMemory(RamTechnology.DDR4, null)));
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "  "})
  @DisplayName("Debe rechazar una capacidad vacía")
  void shouldRejectEmptyCapacity(String capacity) {
    assertThrows(
        InvalidComputerMemoryException.class,
        () -> new ComputerMemory(RamTechnology.DDR4, capacity));
  }

  @ParameterizedTest
  @ValueSource(strings = {"16", "16GB extra", "16gb", "GB"})
  @DisplayName("Debe rechazar capacidades con formato inválido")
  void shouldRejectInvalidCapacityFormat(String capacity) {
    assertThrows(
        InvalidComputerMemoryException.class,
        () -> new ComputerMemory(RamTechnology.DDR4, capacity));
  }
}
