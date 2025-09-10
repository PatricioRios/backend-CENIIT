package ar.edu.ceniit.demo.auth.aplication.ports.input;

import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.UserNotFoundInProviderException;

import java.util.UUID;

public interface DeleteUserUseCase {
    void deleteUser(UUID userId) throws
            UserNotFoundInProviderException;
}
