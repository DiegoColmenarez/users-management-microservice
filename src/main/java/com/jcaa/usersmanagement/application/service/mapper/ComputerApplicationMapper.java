package com.jcaa.usersmanagement.application.service.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateComputerCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteComputerCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateComputerCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetComputerByIdQuery;
import com.jcaa.usersmanagement.domain.enums.Category;
import com.jcaa.usersmanagement.domain.enums.DiskTechnology;
import com.jcaa.usersmanagement.domain.enums.RamTechnology;
import com.jcaa.usersmanagement.domain.model.ComputerModel;
import com.jcaa.usersmanagement.domain.valueobject.*;
import lombok.experimental.UtilityClass;


@UtilityClass
public class ComputerApplicationMapper {

    public ComputerModel fromCreateCommandToModel(final CreateComputerCommand command) {
        return ComputerModel.createNew(
                new ComputerBrand(command.brand()),
                Category.fromValue(command.category()),
                new ComputerProcessor(command.cpuBrand(), command.cpuSpeed()),
                new ComputerMemory(RamTechnology.fromValue(command.ramTechnology()), command.ramCapacity()),
                new ComputerStorage(DiskTechnology.fromValue(command.diskTechnology()), command.diskCapacity()),
                new ComputerDisplay(command.monitorBrand(), command.monitorInches()),
                new ComputerPorts(command.usbPorts(), command.hdmiPorts()),
                new ComputerPrice(command.price()));
    }

    public ComputerModel fromUpdateCommandToModel(final UpdateComputerCommand command) {
        return new ComputerModel(
                ComputerId.fromString(command.id()),
                Category.fromValue(command.category()),
                new ComputerBrand(command.brand()),
                new ComputerProcessor(command.cpuBrand(), command.cpuSpeed()),
                new ComputerMemory(RamTechnology.fromValue(command.ramTechnology()), command.ramCapacity()),
                new ComputerStorage(DiskTechnology.fromValue(command.diskTechnology()), command.diskCapacity()),
                new ComputerDisplay(command.monitorBrand(), command.monitorInches()),
                new ComputerPorts(command.usbPorts(), command.hdmiPorts()),
                new ComputerPrice(command.price()));
    }

    public ComputerId fromGetComputerByIdQueryToComputerId(final GetComputerByIdQuery query) {
        return ComputerId.fromString(query.id());
    }

    public ComputerId fromDeleteCommandToComputerId(final DeleteComputerCommand command) {
        return ComputerId.fromString(command.id());
    }
}