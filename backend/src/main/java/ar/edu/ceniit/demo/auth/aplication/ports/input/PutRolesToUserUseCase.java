package ar.edu.ceniit.demo.auth.aplication.ports.input;

import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.BadRequest;
import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.UserNotFoundInProviderException;

import java.util.Set;
import java.util.UUID;

public interface PutRolesToUserUseCase {
    void putRolesToUser(UUID uuid, Set<String> roles) throws UserNotFoundInProviderException, BadRequest;
}
