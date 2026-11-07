package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidComputerBrandException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("ComputerBrand")
class ComputerBrandTest {

  @ParameterizedTest
  @ValueSource(strings = {" Lenovo ", "acer", "HP-1"})
  @DisplayName("Debe recortar espacios y normalizar la marca a mayúsculas")
  void shouldNormalizeBrand(String input) {
    ComputerBrand brand = new ComputerBrand(input);

    assertEquals(input.trim().toUpperCase(), brand.value());
  }

  @Test
  @DisplayName("Debe rechazar una marca null")
  void shouldRejectNullBrand() {
    assertThrows(InvalidComputerBrandException.class, () -> new ComputerBrand(null));
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "   "})
  @DisplayName("Debe rechazar una marca vacía")
  void shouldRejectEmptyBrand(String input) {
    assertThrows(InvalidComputerBrandException.class, () -> new ComputerBrand(input));
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "A",
        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
        "A&B"
      })
  @DisplayName("Debe rechazar marcas demasiado cortas, largas o con formato inválido")
  void shouldRejectInvalidBrand(String input) {
    assertThrows(InvalidComputerBrandException.class, () -> new ComputerBrand(input));
  }
}
