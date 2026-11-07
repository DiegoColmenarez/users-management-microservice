package com.jcaa.usersmanagement.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.enums.Category;
import com.jcaa.usersmanagement.domain.enums.DiskTechnology;
import com.jcaa.usersmanagement.domain.enums.RamTechnology;
import com.jcaa.usersmanagement.domain.valueobject.*;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ComputerModel")
class ComputerModelTest {

  private ComputerBrand brand;
  private Category category;
  private ComputerProcessor processor;
  private ComputerMemory memory;
  private ComputerStorage storage;
  private ComputerDisplay display;
  private ComputerPorts ports;
  private ComputerPrice price;

  @BeforeEach
  void setUp() {
    brand = new ComputerBrand("Lenovo");
    category = Category.GAMING;
    processor = new ComputerProcessor("Intel", "3.5GHz");
    memory = new ComputerMemory(RamTechnology.DDR5, "16GB");
    storage = new ComputerStorage(DiskTechnology.SSD, "1TB");
    display = new ComputerDisplay("Dell", new BigDecimal("27"));
    ports = new ComputerPorts(4, 1);
    price = new ComputerPrice(new BigDecimal("1299.99"));
  }

  @Test
  @DisplayName("Constructor debe preservar el identificador y todos los valores")
  void shouldPreserveFieldsFromConstructor() {
    ComputerId id = new ComputerId(UUID.randomUUID());

    ComputerModel computer = new ComputerModel(
            id, category, brand, processor, memory, storage, display, ports, price
    );

    assertAll(
            () -> assertSame(id, computer.getId()),
            () -> assertSame(category, computer.getComputerCategory()),
            () -> assertSame(brand, computer.getBrand()),
            () -> assertSame(processor, computer.getProcessor()),
            () -> assertSame(memory, computer.getMemory()),
            () -> assertSame(storage, computer.getStorage()),
            () -> assertSame(display, computer.getDisplay()),
            () -> assertSame(ports, computer.getPorts()),
            () -> assertSame(price, computer.getPrice())
    );
  }

  @Test
  @DisplayName("createNew debe generar un identificador y preservar los valores")
  void shouldCreateNewComputerWithGeneratedIdAndGivenValues() {
    ComputerModel computer = ComputerModel.createNew(
            brand, category, processor, memory, storage, display, ports, price
    );

    assertAll(
            () -> assertNotNull(computer.getId()),
            () -> assertNotNull(computer.getId().value()),
            () -> assertSame(category, computer.getComputerCategory()),
            () -> assertSame(brand, computer.getBrand()),
            () -> assertSame(processor, computer.getProcessor()),
            () -> assertSame(memory, computer.getMemory()),
            () -> assertSame(storage, computer.getStorage()),
            () -> assertSame(display, computer.getDisplay()),
            () -> assertSame(ports, computer.getPorts()),
            () -> assertSame(price, computer.getPrice())
    );
  }

  @Test
  @DisplayName("La igualdad del modelo depende únicamente del identificador")
  void shouldCompareComputersByIdOnly() {
    ComputerId id = new ComputerId(UUID.randomUUID());
    ComputerModel first = new ComputerModel(id, category, brand, processor, memory, storage, display, ports, price);

    ComputerModel sameId = new ComputerModel(
            id,
            Category.OFFICE,
            new ComputerBrand("HP"),
            new ComputerProcessor("AMD", "4GHz"),
            memory,
            storage,
            display,
            ports,
            new ComputerPrice(new BigDecimal("999.99"))
    );

    ComputerModel differentId = new ComputerModel(
            new ComputerId(UUID.randomUUID()), category, brand, processor, memory, storage, display, ports, price
    );

    assertAll(
            () -> assertEquals(first, sameId),
            () -> assertEquals(first.hashCode(), sameId.hashCode()),
            () -> assertNotEquals(first, differentId)
    );
  }

  @Test
  @DisplayName("updatePrice debe reemplazar el precio")
  void shouldUpdatePrice() {
    ComputerModel computer = createComputer();
    ComputerPrice newPrice = new ComputerPrice(new BigDecimal("1499.50"));

    computer.updatePrice(newPrice);

    assertSame(newPrice, computer.getPrice());
  }

  @Test
  @DisplayName("updatePrice no debe aceptar null")
  void shouldRejectNullPriceUpdate() {
    ComputerModel computer = createComputer();

    assertThrows(
            NullPointerException.class,
            () -> computer.updatePrice(null)
    );
  }

  @Test
  @DisplayName("upgradeHardware debe reemplazar procesador, memoria y almacenamiento")
  void shouldUpgradeHardware() {
    ComputerModel computer = createComputer();
    ComputerProcessor newProcessor = new ComputerProcessor("AMD", "4GHz");
    ComputerMemory newMemory = new ComputerMemory(RamTechnology.DDR5, "32GB");
    ComputerStorage newStorage = new ComputerStorage(DiskTechnology.NVME, "2TB");

    computer.upgradeHardware(newProcessor, newMemory, newStorage);

    assertAll(
            () -> assertSame(newProcessor, computer.getProcessor()),
            () -> assertSame(newMemory, computer.getMemory()),
            () -> assertSame(newStorage, computer.getStorage())
    );
  }

  @Test
  @DisplayName("upgradeHardware debe rechazar valores null")
  void shouldRejectNullHardwareUpdates() {
    ComputerModel computer = createComputer();

    assertAll(
            () -> assertThrows(NullPointerException.class, () -> computer.upgradeHardware(null, memory, storage)),
            () -> assertThrows(NullPointerException.class, () -> computer.upgradeHardware(processor, null, storage)),
            () -> assertThrows(NullPointerException.class, () -> computer.upgradeHardware(processor, memory, null))
    );
  }

  @Test
  @DisplayName("updatePeripherals debe reemplazar pantalla y puertos")
  void shouldUpdatePeripherals() {
    ComputerModel computer = createComputer();
    ComputerDisplay newDisplay = new ComputerDisplay("LG", new BigDecimal("32"));
    ComputerPorts newPorts = new ComputerPorts(6, 2);

    computer.updatePeripherals(newDisplay, newPorts);

    assertAll(
            () -> assertSame(newDisplay, computer.getDisplay()),
            () -> assertSame(newPorts, computer.getPorts())
    );
  }

  @Test
  @DisplayName("updatePeripherals debe rechazar valores null")
  void shouldRejectNullPeripheralUpdates() {
    ComputerModel computer = createComputer();

    assertAll(
            () -> assertThrows(NullPointerException.class, () -> computer.updatePeripherals(null, ports)),
            () -> assertThrows(NullPointerException.class, () -> computer.updatePeripherals(display, null))
    );
  }

  private ComputerModel createComputer() {
    return new ComputerModel(
            new ComputerId(UUID.randomUUID()), category, brand, processor, memory, storage, display, ports, price
    );
  }
}