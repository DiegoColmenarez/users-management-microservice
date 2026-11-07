package com.jcaa.usersmanagement.domain.model;

import com.jcaa.usersmanagement.domain.enums.Category;
import com.jcaa.usersmanagement.domain.valueobject.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import java.util.Objects;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ComputerModel {

    @EqualsAndHashCode.Include
    private final ComputerId id;
    private Category computerCategory;
    private ComputerBrand brand;
    private ComputerProcessor processor;
    private ComputerMemory memory;
    private ComputerStorage storage;
    private ComputerDisplay display;
    private ComputerPorts ports;
    private ComputerPrice price;

    public ComputerModel(
            ComputerId id,
            Category computerCategory,
            ComputerBrand brand,
            ComputerProcessor processor,
            ComputerMemory memory,
            ComputerStorage storage,
            ComputerDisplay display,
            ComputerPorts ports,
            ComputerPrice price) {

        this.id = id;
        this.computerCategory = computerCategory;
        this.brand = brand;
        this.processor = processor;
        this.memory = memory;
        this.storage = storage;
        this.display = display;
        this.ports = ports;
        this.price = price;
    }

    public static ComputerModel createNew(
            ComputerBrand brand,
            Category computerCategory,
            ComputerProcessor processor,
            ComputerMemory memory,
            ComputerStorage storage,
            ComputerDisplay display,
            ComputerPorts ports,
            ComputerPrice price) {

        return new ComputerModel(
                        ComputerId.generate(),
                computerCategory,
                brand,
                processor,
                memory,
                storage,
                display,
                ports,
                price
                );
    }

    public void updatePrice(ComputerPrice newPrice) {
        this.price = Objects.requireNonNull(newPrice, "New price cannot be null");
    }

    public void upgradeHardware(
            ComputerProcessor newProcessor,
            ComputerMemory newMemory,
            ComputerStorage newStorage) {
        this.processor = Objects.requireNonNull(newProcessor, "New processor cannot be null");
        this.memory = Objects.requireNonNull(newMemory, "New memory cannot be null");
        this.storage = Objects.requireNonNull(newStorage, "New storage cannot be null");
    }

    public void updatePeripherals(ComputerDisplay newDisplay, ComputerPorts newPorts) {
        this.display = Objects.requireNonNull(newDisplay, "New display cannot be null");
        this.ports = Objects.requireNonNull(newPorts, "New ports cannot be null");
    }
}
