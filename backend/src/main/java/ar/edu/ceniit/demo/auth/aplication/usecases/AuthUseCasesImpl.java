package ar.edu.ceniit.demo.auth.aplication.usecases;

import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.BadRequest;
import ar.edu.ceniit.demo.auth.aplication.ports.input.AuthUseCases;
import ar.edu.ceniit.demo.auth.aplication.ports.input.dto.RegisterUserCommand;
import ar.edu.ceniit.demo.auth.aplication.ports.input.dto.UserForDomain;
import ar.edu.ceniit.demo.auth.aplication.ports.output.AuthOutputs;
import ar.edu.ceniit.demo.auth.aplication.ports.output.CreateUserOnDomainOutput;
import ar.edu.ceniit.demo.auth.aplication.ports.output.dto.AuthUserResponse;
import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.AuthException;

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
    public void register(RegisterUserCommand command) throws AuthException, Exception {
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
        } catch (AuthException e) {
            // Paso 3 (Compensación): Si falla la creación local, eliminar el usuario del IdP
            try {
                authOutputs.deleteUserOnIdentityProvider(createdAuthUser.getId());
            } catch (AuthException compensationException) {
                // Si la compensación falla, se debe registrar el error de forma crítica
                throw new Exception("Error de registro. Falla crítica en la compensación: el usuario "
                        + createdAuthUser.getUsername() + " existe en el IdP pero no en la base de datos local.", compensationException);
            }
            //throw new AuthException("Error al registrar el usuario en la base de datos local.", e);
            throw e;
        }
    }

    @Override
    public void putRolesToUser(UUID uuid, Set<String> roles) throws Exception {
        if(uuid == null) {
            throw new BadRequest("El UUID no puede ser nulo.");
        }
        authOutputs.putRolesToUser(uuid, roles);
    }

    @Override
    public Set<String> getAllRoles() throws Exception {
        return authOutputs.getAllRoles();
    }
}