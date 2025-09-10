package ar.edu.ceniit.demo.user.aplication.ports.output;

import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserNotFoundException;

import java.util.UUID;

public interface DeleteUserByUUIDOutput {
    void deleteUserByUUID(UUID uuid)
            throws UserNotFoundException;
}