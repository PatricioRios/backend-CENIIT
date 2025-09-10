package ar.edu.ceniit.demo.auth.aplication.ports.output;

import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.AuthException;
import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.UserNotFoundInProviderException;

import java.util.UUID;

public interface DeleteUserOnIdentityProviderOutput {
    void deleteUserOnIdentityProvider(UUID userId) throws UserNotFoundInProviderException;
}
