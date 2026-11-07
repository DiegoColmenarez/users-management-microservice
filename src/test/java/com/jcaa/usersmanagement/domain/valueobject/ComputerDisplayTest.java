package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidComputerDisplayException;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("ComputerDisplay")
class ComputerDisplayTest {

  @Test
  @DisplayName("Debe recortar y normalizar la marca, preservando las pulgadas")
  void shouldNormalizeBrandAndPreserveInches() {
    BigDecimal inches = new BigDecimal("27.5");

    ComputerDisplay display = new ComputerDisplay(" Dell ", inches);

    assertAll(
        () -> assertEquals("DELL", display.monitorBrand()),
        () -> assertSame(inches, display.inches()));
  }

  @Test
  @DisplayName("Debe rechazar una marca null o vacía")
  void shouldRejectNullOrEmptyBrand() {
    assertAll(
        () ->
            assertThrows(
                InvalidComputerDisplayException.class,
                () -> new ComputerDisplay(null, BigDecimal.TEN)),
        () ->
            assertThrows(
                InvalidComputerDisplayException.class,
                () -> new ComputerDisplay("  ", BigDecimal.TEN)));
  }

  @Test
  @DisplayName("Debe rechazar pulgadas null")
  void shouldRejectNullInches() {
    assertThrows(
        InvalidComputerDisplayException.class, () -> new ComputerDisplay("Dell", null));
  }

  @ParameterizedTest
  @ValueSource(strings = {"0", "-1", "80.01"})
  @DisplayName("Debe rechazar pulgadas menores o iguales a cero y mayores a 80")
  void shouldRejectInchesOutsideRange(String value) {
    assertThrows(
        InvalidComputerDisplayException.class,
        () -> new ComputerDisplay("Dell", new BigDecimal(value)));
  }

  @Test
  @DisplayName("Debe aceptar los límites válidos de pulgadas")
  void shouldAcceptInchesAtValidLimits() {
    assertAll(
        () -> assertEquals(new BigDecimal("0.1"), new ComputerDisplay("Dell", new BigDecimal("0.1")).inches()),
        () -> assertEquals(new BigDecimal("80"), new ComputerDisplay("Dell", new BigDecimal("80")).inches()));
  }
}
