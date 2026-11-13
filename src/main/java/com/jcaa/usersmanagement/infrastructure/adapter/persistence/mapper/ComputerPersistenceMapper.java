package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import com.jcaa.usersmanagement.domain.enums.Category;
import com.jcaa.usersmanagement.domain.enums.DiskTechnology;
import com.jcaa.usersmanagement.domain.enums.RamTechnology;
import com.jcaa.usersmanagement.domain.model.ComputerModel;
import com.jcaa.usersmanagement.domain.valueobject.*;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.ComputerEntity;
import lombok.experimental.UtilityClass;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ComputerPersistenceMapper {

    public ComputerEntity fromModelToEntity(final ComputerModel computer) {
        return new ComputerEntity(
                computer.getId().value().toString(),
                computer.getComputerCategory().name(),
                computer.getBrand().value(),
                computer.getProcessor().cpuBrand(),
                computer.getProcessor().cpuSpeed(),
                computer.getMemory().ramTechnology().name(),
                computer.getMemory().ramCapacity(),
                computer.getStorage().diskTechnology().name(),
                computer.getStorage().diskCapacity(),
                computer.getDisplay().monitorBrand(),
                computer.getDisplay().inches(),
                computer.getPorts().usbPorts(),
                computer.getPorts().hdmiPorts(),
                computer.getPrice().value());
    }

    public ComputerEntity fromResultSetToEntity(final ResultSet rs) throws SQLException {
        return new ComputerEntity(
                rs.getString("id"), rs.getString("category"), rs.getString("brand"),
                rs.getString("cpu_brand"), rs.getString("cpu_speed"),
                rs.getString("ram_technology"), rs.getString("ram_capacity"),
                rs.getString("disk_technology"), rs.getString("disk_capacity"),
                rs.getString("monitor_brand"), rs.getBigDecimal("monitor_inches"),
                rs.getInt("usb_ports"), rs.getInt("hdmi_ports"),
                rs.getBigDecimal("price"));
    }

    public ComputerModel fromEntityToModel(final ComputerEntity e) {
        return new ComputerModel(
                ComputerId.fromString(e.id()),
                Category.fromValue(e.category()),
                new ComputerBrand(e.brand()),
                new ComputerProcessor(e.cpuBrand(), e.cpuSpeed()),
                new ComputerMemory(RamTechnology.fromValue(e.ramTechnology()), e.ramCapacity()),
                new ComputerStorage(DiskTechnology.fromValue(e.diskTechnology()), e.diskCapacity()),
                new ComputerDisplay(e.monitorBrand(), e.monitorInches()),
                new ComputerPorts(e.usbPorts(), e.hdmiPorts()),
                new ComputerPrice(e.price()));
    }

    public ComputerModel fromResultSetToModel(final ResultSet rs) throws SQLException {
        return fromEntityToModel(fromResultSetToEntity(rs));
    }

    public List<ComputerModel> fromResultSetToModelList(final ResultSet rs) throws SQLException {
        final List<ComputerModel> computers = new ArrayList<>();
        while (rs.next()) {
            computers.add(fromResultSetToModel(rs));
        }
        return computers;
    }
}
