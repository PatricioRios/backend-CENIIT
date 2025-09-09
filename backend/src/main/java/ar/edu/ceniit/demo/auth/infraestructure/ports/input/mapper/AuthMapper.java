package ar.edu.ceniit.demo.auth.infraestructure.ports.input.mapper;

import ar.edu.ceniit.demo.auth.aplication.ports.input.dto.RegisterUserCommand;
import ar.edu.ceniit.demo.auth.infraestructure.ports.input.dto.RegisterUserRequest;

public class AuthMapper {
    public RegisterUserCommand toDomain(RegisterUserRequest registerUserRequest) {
        return RegisterUserCommand.builder()
                .username(registerUserRequest.getUsername())
                .password(registerUserRequest.getPassword())
                .email(registerUserRequest.getEmail())
                .firstName(registerUserRequest.getFirstName())
                .lastName(registerUserRequest.getLastName())
                .build();
    }
}
