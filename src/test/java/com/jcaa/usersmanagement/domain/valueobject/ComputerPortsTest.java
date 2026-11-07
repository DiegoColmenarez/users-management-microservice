package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidComputerPortsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ComputerPorts")
class ComputerPortsTest {

  @Test
  @DisplayName("Debe conservar la cantidad de puertos indicada")
  void shouldPreservePortCounts() {
    ComputerPorts ports = new ComputerPorts(4, 2);

    assertAll(
        () -> assertEquals(4, ports.usbPorts()),
        () -> assertEquals(2, ports.hdmiPorts()));
  }

  @Test
  @DisplayName("Debe aceptar cero puertos y un total de 20")
  void shouldAcceptZeroAndMaximumTotalPorts() {
    ComputerPorts maximum = new ComputerPorts(18, 2);

    assertAll(
        () -> assertEquals(0, new ComputerPorts(0, 0).usbPorts()),
        () -> assertEquals(20, maximum.usbPorts() + maximum.hdmiPorts()));
  }

  @Test
  @DisplayName("Debe rechazar cantidades null")
  void shouldRejectNullPortCounts() {
    assertAll(
        () ->
            assertThrows(
                InvalidComputerPortsException.class, () -> new ComputerPorts(null, 1)),
        () ->
            assertThrows(
                InvalidComputerPortsException.class, () -> new ComputerPorts(1, null)));
  }

  @Test
  @DisplayName("Debe rechazar cantidades negativas")
  void shouldRejectNegativePortCounts() {
    assertAll(
        () ->
            assertThrows(
                InvalidComputerPortsException.class, () -> new ComputerPorts(-1, 0)),
        () ->
            assertThrows(
                InvalidComputerPortsException.class, () -> new ComputerPorts(0, -1)));
  }

  @Test
  @DisplayName("Debe rechazar un total de puertos mayor a 20")
  void shouldRejectTotalAboveMaximum() {
    assertThrows(
        InvalidComputerPortsException.class, () -> new ComputerPorts(19, 2));
  }
}
