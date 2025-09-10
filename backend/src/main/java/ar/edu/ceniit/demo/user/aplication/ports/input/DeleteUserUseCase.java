package ar.edu.ceniit.demo.user.aplication.ports.input;

import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserBadRequestException;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserNotFoundException;

import java.util.UUID;

public interface DeleteUserUseCase {
    void deleteUser(UUID uuid)
            throws UserNotFoundException, UserBadRequestException;
}