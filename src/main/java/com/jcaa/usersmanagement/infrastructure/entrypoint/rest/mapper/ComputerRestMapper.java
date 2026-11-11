package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateComputerCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteComputerCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateComputerCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetComputerByIdQuery;
import com.jcaa.usersmanagement.domain.model.ComputerModel;
import com.jcaa.usersmanagement.domain.valueobject.ComputerId;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.ComputerRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.ComputerIdRestResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.ComputerRestResponse;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class ComputerRestMapper {

    public CreateComputerCommand toCreateCommand(final ComputerRestRequest r) {
        return new CreateComputerCommand(
                r.category(), r.brand(), r.cpuBrand(), r.cpuSpeed(),
                r.ramTechnology(), r.ramCapacity(), r.diskTechnology(), r.diskCapacity(),
                r.monitorBrand(), r.monitorInches(), r.usbPorts(), r.hdmiPorts(), r.price());
    }

    public UpdateComputerCommand toUpdateCommand(final String id, final ComputerRestRequest r) {
        return new UpdateComputerCommand(
                id, r.category(), r.brand(), r.cpuBrand(), r.cpuSpeed(),
                r.ramTechnology(), r.ramCapacity(), r.diskTechnology(), r.diskCapacity(),
                r.monitorBrand(), r.monitorInches(), r.usbPorts(), r.hdmiPorts(), r.price());
    }

    public GetComputerByIdQuery toGetByIdQuery(final String id) {
        return new GetComputerByIdQuery(id);
    }

    public DeleteComputerCommand toDeleteCommand(final String id) {
        return new DeleteComputerCommand(id);
    }

    public ComputerIdRestResponse toIdResponse(final ComputerId id) {
        return new ComputerIdRestResponse(id.value().toString());
    }

    public ComputerRestResponse toResponse(final ComputerModel c) {
        return new ComputerRestResponse(
                c.getId().value().toString(),
                c.getComputerCategory().name(),
                c.getBrand().value(),
                c.getProcessor().cpuBrand(),
                c.getProcessor().cpuSpeed(),
                c.getMemory().ramTechnology().name(),
                c.getMemory().ramCapacity(),
                c.getStorage().diskTechnology().name(),
                c.getStorage().diskCapacity(),
                c.getDisplay().monitorBrand(),
                c.getDisplay().inches(),
                c.getPorts().usbPorts(),
                c.getPorts().hdmiPorts(),
                c.getPrice().value());
    }

    public List<ComputerRestResponse> toResponseList(final List<ComputerModel> computers) {
        return computers.stream().map(ComputerRestMapper::toResponse).toList();
    }
}
