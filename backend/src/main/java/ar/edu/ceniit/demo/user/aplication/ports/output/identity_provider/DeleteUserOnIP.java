package ar.edu.ceniit.demo.user.aplication.ports.output.identity_provider;

import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserNotFoundException;

import java.util.UUID;

public interface DeleteUserOnIP {
    void deleteUser(UUID uuidOnIP) throws UserNotFoundException;
}
