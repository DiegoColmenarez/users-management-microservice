package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.enums.DiskTechnology;
import com.jcaa.usersmanagement.domain.exception.InvalidComputerStorageException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("ComputerStorage")
class ComputerStorageTest {

  @Test
  @DisplayName("Debe normalizar espacios en los extremos y mayúsculas")
  void shouldNormalizeCapacity() {
    ComputerStorage storage = new ComputerStorage(DiskTechnology.NVME, " 2TB ");

    assertAll(
        () -> assertEquals(DiskTechnology.NVME, storage.diskTechnology()),
        () -> assertEquals("2TB", storage.diskCapacity()));
  }

  @Test
  @DisplayName("Debe rechazar tecnología o capacidad null")
  void shouldRejectNullTechnologyOrCapacity() {
    assertAll(
        () ->
            assertThrows(
                InvalidComputerStorageException.class,
                () -> new ComputerStorage(null, "1TB")),
        () ->
            assertThrows(
                InvalidComputerStorageException.class,
                () -> new ComputerStorage(DiskTechnology.SSD, null)));
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "  "})
  @DisplayName("Debe rechazar una capacidad vacía")
  void shouldRejectEmptyCapacity(String capacity) {
    assertThrows(
        InvalidComputerStorageException.class,
        () -> new ComputerStorage(DiskTechnology.SSD, capacity));
  }

  @ParameterizedTest
  @ValueSource(strings = {"1", "1GB extra", "1tb", "TB"})
  @DisplayName("Debe rechazar capacidades con formato inválido")
  void shouldRejectInvalidCapacityFormat(String capacity) {
    assertThrows(
        InvalidComputerStorageException.class,
        () -> new ComputerStorage(DiskTechnology.SSD, capacity));
  }

  @Test
  @DisplayName("Debe aceptar las unidades GB, MB y TB")
  void shouldAcceptSupportedCapacityUnits() {
    assertAll(
        () ->
            assertEquals(
                "500MB",
                new ComputerStorage(DiskTechnology.HDD, "500MB").diskCapacity()),
        () ->
            assertEquals(
                "1GB",
                new ComputerStorage(DiskTechnology.SSD, "1GB").diskCapacity()),
        () ->
            assertEquals(
                "1TB",
                new ComputerStorage(DiskTechnology.NVME, "1TB").diskCapacity()));
  }
}
