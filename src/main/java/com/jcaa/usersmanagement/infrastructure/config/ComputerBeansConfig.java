package com.jcaa.usersmanagement.infrastructure.config;

import com.jcaa.usersmanagement.application.port.in.*;
import com.jcaa.usersmanagement.application.port.out.*;
import com.jcaa.usersmanagement.application.service.*;
import jakarta.validation.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ComputerBeansConfig {

    @Bean
    CreateComputerUseCase createComputerUseCase(SaveComputerPort saveComputerPort, Validator validator) {
        return new CreateComputerService(saveComputerPort, validator);
    }

    @Bean
    UpdateComputerUseCase updateComputerUseCase(
            UpdateComputerPort updateComputerPort, GetComputerByIdPort getComputerByIdPort, Validator validator) {
        return new UpdateComputerService(updateComputerPort, getComputerByIdPort, validator);
    }

    @Bean
    DeleteComputerUseCase deleteComputerUseCase(
            DeleteComputerPort deleteComputerPort, GetComputerByIdPort getComputerByIdPort, Validator validator) {
        return new DeleteComputerService(deleteComputerPort, getComputerByIdPort, validator);
    }

    @Bean
    GetComputerByIdUseCase getComputerByIdUseCase(GetComputerByIdPort getComputerByIdPort, Validator validator) {
        return new GetComputerByIdService(getComputerByIdPort, validator);
    }

    @Bean
    GetAllComputersUseCase getAllComputersUseCase(GetAllComputersPort getAllComputersPort) {
        return new GetAllComputersService(getAllComputersPort);
    }
}
