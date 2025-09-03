package ar.edu.ceniit.demo.auth.aplication.ports.output;

import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.AuthException;

import java.util.UUID;

public interface DeleteUserOnIdentityProvider {
    void deleteUserOnIdentityProvider(UUID userId) throws AuthException;
}
