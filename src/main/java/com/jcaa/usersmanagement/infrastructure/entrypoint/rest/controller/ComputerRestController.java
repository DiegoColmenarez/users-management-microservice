package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.controller;

import com.jcaa.usersmanagement.application.port.in.*;
import com.jcaa.usersmanagement.domain.valueobject.ComputerId;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.ComputerRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.ComputerIdRestResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.ComputerRestResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.mapper.ComputerRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/computers")
@RequiredArgsConstructor
public class ComputerRestController {

    private final CreateComputerUseCase createComputerUseCase;
    private final UpdateComputerUseCase updateComputerUseCase;
    private final DeleteComputerUseCase deleteComputerUseCase;
    private final GetComputerByIdUseCase getComputerByIdUseCase;
    private final GetAllComputersUseCase getAllComputersUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComputerIdRestResponse create(@Valid @RequestBody final ComputerRestRequest request) {
        final ComputerId id = createComputerUseCase.execute(ComputerRestMapper.toCreateCommand(request));
        return ComputerRestMapper.toIdResponse(id);
    }

    @GetMapping
    public List<ComputerRestResponse> getAll() {
        return ComputerRestMapper.toResponseList(getAllComputersUseCase.execute());
    }

    @GetMapping("/{id}")
    public ComputerRestResponse getById(@PathVariable final String id) {
        return ComputerRestMapper.toResponse(
                getComputerByIdUseCase.execute(ComputerRestMapper.toGetByIdQuery(id)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable final String id, @Valid @RequestBody final ComputerRestRequest request) {
        updateComputerUseCase.execute(ComputerRestMapper.toUpdateCommand(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final String id) {
        deleteComputerUseCase.execute(ComputerRestMapper.toDeleteCommand(id));
    }
}