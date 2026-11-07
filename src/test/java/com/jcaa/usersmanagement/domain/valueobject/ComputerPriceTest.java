package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.exception.InvalidComputerPriceException;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("ComputerPrice")
class ComputerPriceTest {

  @Test
  @DisplayName("Debe conservar un precio válido")
  void shouldPreserveValidPrice() {
    BigDecimal value = new BigDecimal("1299.99");

    ComputerPrice price = new ComputerPrice(value);

    assertSame(value, price.value());
  }

  @Test
  @DisplayName("Debe rechazar un precio null")
  void shouldRejectNullPrice() {
    assertThrows(InvalidComputerPriceException.class, () -> new ComputerPrice(null));
  }

  @ParameterizedTest
  @ValueSource(strings = {"0", "-0.01"})
  @DisplayName("Debe rechazar precios menores o iguales a cero")
  void shouldRejectNonPositivePrice(String value) {
    assertThrows(
        InvalidComputerPriceException.class,
        () -> new ComputerPrice(new BigDecimal(value)));
  }

  @Test
  @DisplayName("Debe rechazar un precio superior al máximo permitido")
  void shouldRejectPriceAboveMaximum() {
    assertThrows(
        InvalidComputerPriceException.class,
        () -> new ComputerPrice(new BigDecimal("1000000.01")));
  }

  @Test
  @DisplayName("Debe rechazar precios con más de dos decimales")
  void shouldRejectPriceWithMoreThanTwoDecimals() {
    assertThrows(
        InvalidComputerPriceException.class,
        () -> new ComputerPrice(new BigDecimal("10.001")));
  }

  @Test
  @DisplayName("Debe aceptar precios en los límites válidos")
  void shouldAcceptValidPriceLimits() {
    assertAll(
        () -> assertEquals(new BigDecimal("0.01"), new ComputerPrice(new BigDecimal("0.01")).value()),
        () ->
            assertEquals(
                new BigDecimal("1000000"),
                new ComputerPrice(new BigDecimal("1000000")).value()));
  }
}
