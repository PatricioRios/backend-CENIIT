package ar.edu.ceniit.demo.auth.aplication.usecases;

import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.*;
import ar.edu.ceniit.demo.auth.aplication.ports.input.AuthUseCases;
import ar.edu.ceniit.demo.auth.aplication.ports.input.dto.RegisterUserCommand;
import ar.edu.ceniit.demo.auth.aplication.ports.input.dto.UserForDomain;
import ar.edu.ceniit.demo.auth.aplication.ports.output.AuthOutputs;
import ar.edu.ceniit.demo.auth.aplication.ports.output.CreateUserOnDomainOutput;
import ar.edu.ceniit.demo.auth.aplication.ports.output.dto.AuthUserResponse;
import ar.edu.ceniit.demo.common.exceptions.FatalErrorException;
import ar.edu.ceniit.demo.common.exceptions.WarningErrorException;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class AuthUseCasesImpl implements AuthUseCases {
    private final AuthOutputs authOutputs;
    private final CreateUserOnDomainOutput createUserOnDomainOutput;
    public AuthUseCasesImpl(AuthOutputs authOutputs, CreateUserOnDomainOutput createUserOnDomainOutput) {
        this.authOutputs = authOutputs;
        this.createUserOnDomainOutput = createUserOnDomainOutput;
    }
    @Override
    public UUID register(RegisterUserCommand command) throws
            BadRequestOnRegisterUserException,
            UserNameIsAlreadyInUse,// ver que onda
            DuplicatedEmailException// ver que onda
         {

        // Validaciones básicas
        validateRequest(command);

        // Construir el DTO para el proveedor de identidad
        AuthUserResponse authUserRequest = AuthUserResponse.builder()
                .username(command.getUsername())
                .email(command.getEmail())
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .password(command.getPassword())
                .build();

        // Paso 1: Registrar en el proveedor de identidad (Keycloak)
        AuthUserResponse createdAuthUser = authOutputs.registerUserOnIdentityProvider(authUserRequest);

        try {
            // Paso 2: Crear el usuario en la base de datos local
            UserForDomain userToCreate = new UserForDomain();
            userToCreate.setUuid(createdAuthUser.getId());
            userToCreate.setUsername(createdAuthUser.getUsername());
            userToCreate.setEmail(createdAuthUser.getEmail());
            userToCreate.setFirstName(createdAuthUser.getFirstName());
            userToCreate.setLastName(createdAuthUser.getLastName());
            // La contraseña no se guarda en la BD local, la maneja Keycloak
            userToCreate.setPassword(null);

            createUserOnDomainOutput.createUser(userToCreate);
            return createdAuthUser.getId();
        } catch (Exception e) {
            // Paso 3 (Compensación): Si falla la creación local, eliminar el usuario del IdP
            try {
                System.out.println("Falla en la creación local, intentando eliminar el usuario del IdP: " + createdAuthUser.getUsername());
                authOutputs.deleteUserOnIdentityProvider(createdAuthUser.getId());
                System.out.println("ELIMINACION EXITOSA");
                System.out.println("Usuario eliminado del IdP: " + createdAuthUser.getUsername());
            } catch (Exception compensationException) {
                // Si la compensación falla, se debe registrar el error de forma crítica
                throw new FatalErrorException(
                        "Error crítico: No se pudo registrar el usuario localmente ni eliminarlo del IdP. " +
                                "Se requiere intervención manual para el usuario: " + createdAuthUser.getUsername(),
                        compensationException,
                        FatalErrorException.FatalErrorType.SYSTEM_INCONSISTENCY
                );
            }
            //throw new AuthException("Error al registrar el usuario en la base de datos local.", e);
            throw new WarningErrorException(e.getMessage(), e);
        }
    }

    private static void validateRequest(RegisterUserCommand command) throws BadRequestOnRegisterUserException {
        if (command.getUsername() == null || command.getUsername().isEmpty()) {
            throw new BadRequestOnRegisterUserException(BadRequestOnRegisterUserException.Reason.INVALID_USERNAME);
        }
        if (command.getEmail() == null || command.getEmail().isEmpty() || !command.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new BadRequestOnRegisterUserException(BadRequestOnRegisterUserException.Reason.INVALID_EMAIL);
        }
        if (command.getFirstName() == null || command.getFirstName().isEmpty()) {
            throw new BadRequestOnRegisterUserException(BadRequestOnRegisterUserException.Reason.INVALID_FIRST_NAME);
        }
        if (command.getLastName() == null || command.getLastName().isEmpty()) {
            throw new BadRequestOnRegisterUserException(BadRequestOnRegisterUserException.Reason.INVALID_LAST_NAME);
        }
        if (command.getPassword() == null || command.getPassword().isEmpty() || command.getPassword().length() <= 8) {
            throw new BadRequestOnRegisterUserException(BadRequestOnRegisterUserException.Reason.INVALID_PASSWORD);
        }
    }

    @Override
    public void putRolesToUser(UUID uuid, Set<String> roles) throws UserNotFoundInProviderException, BadRequest {
        if(uuid == null) {
            throw new BadRequest("El UUID no puede ser nulo.");
        }

        authOutputs.putRolesToUser(uuid, roles);
    }

    @Override
    public Set<String> getAllRoles()  {
        return authOutputs.getAllRoles();
    }

    @Override
    public void deleteUser(UUID userId) throws UserNotFoundInProviderException {
        authOutputs.deleteUserOnIdentityProvider(userId);
    }

    @Override
    public void updateBasicInfoOnUser(UUID uuidOnIP, Optional<String> newEmail, Optional<String> newFirstName, Optional<String> newSecondName) throws UserNotFoundInProviderException, DuplicatedEmailException {
        authOutputs.updateBasicInfoOnUser(uuidOnIP, newEmail, newFirstName, newSecondName);
    }
}