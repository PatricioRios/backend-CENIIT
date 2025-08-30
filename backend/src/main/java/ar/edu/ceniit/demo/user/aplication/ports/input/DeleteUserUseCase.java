package ar.edu.ceniit.demo.user.aplication.ports.input;

import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserBaseException;

import java.util.UUID;

public interface DeleteUserUseCase {
    void deleteUser(UUID uuid) throws UserBaseException;
}
