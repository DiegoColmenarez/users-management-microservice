package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidComputerProcessorException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("ComputerProcessor")
class ComputerProcessorTest {

  @Test
  @DisplayName("Debe normalizar la marca y la velocidad sin unidad")
  void shouldNormalizeBrandAndSpeedWithoutUnit() {
    ComputerProcessor processor = new ComputerProcessor(" Intel ", " 3.5 ");

    assertAll(
        () -> assertEquals("INTEL", processor.cpuBrand()),
        () -> assertEquals("3.5GHZ", processor.cpuSpeed()));
  }

  @Test
  @DisplayName("Debe aceptar velocidad en MHz y normalizar a mayúsculas")
  void shouldAcceptSpeedInMhz() {
    ComputerProcessor processor = new ComputerProcessor("AMD", "3500MHz");

    assertEquals("3500MHZ", processor.cpuSpeed());
  }

  @Test
  @DisplayName("Debe rechazar marca null o vacía")
  void shouldRejectNullOrEmptyBrand() {
    assertAll(
        () ->
            assertThrows(
                InvalidComputerProcessorException.class,
                () -> new ComputerProcessor(null, "3GHz")),
        () ->
            assertThrows(
                InvalidComputerProcessorException.class,
                () -> new ComputerProcessor(" ", "3GHz")));
  }

  @Test
  @DisplayName("Debe rechazar velocidad null o vacía")
  void shouldRejectNullOrEmptySpeed() {
    assertAll(
        () ->
            assertThrows(
                InvalidComputerProcessorException.class,
                () -> new ComputerProcessor("Intel", null)),
        () ->
            assertThrows(
                InvalidComputerProcessorException.class,
                () -> new ComputerProcessor("Intel", " ")));
  }

  @ParameterizedTest
  @ValueSource(strings = {"3GHz extra", "fast", "3.5GH"})
  @DisplayName("Debe rechazar velocidades con formato inválido")
  void shouldRejectInvalidSpeedFormat(String speed) {
    assertThrows(
        InvalidComputerProcessorException.class,
        () -> new ComputerProcessor("Intel", speed));
  }

  @ParameterizedTest
  @ValueSource(strings = {"0.09GHz", "100MHz", "10.1GHz", "11000MHz"})
  @DisplayName("Debe rechazar velocidades fuera del rango permitido")
  void shouldRejectSpeedOutsideRange(String speed) {
    assertThrows(
        InvalidComputerProcessorException.class,
        () -> new ComputerProcessor("Intel", speed));
  }

  @Test
  @DisplayName("Debe aceptar velocidades en los límites válidos")
  void shouldAcceptSpeedLimits() {
    assertAll(
        () -> assertEquals("0.1GHZ", new ComputerProcessor("Intel", "0.1GHz").cpuSpeed()),
        () -> assertEquals("10GHZ", new ComputerProcessor("Intel", "10GHz").cpuSpeed()));
  }
}
