package ar.edu.ceniit.demo.user.aplication.ports.output.identity_provider;

import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.DuplicateEmailException;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserNotFoundException;

import java.util.Optional;
import java.util.UUID;

public interface UpdateUserOnIP {
    void updateUserOnIP(UUID uuidOnIP,
                        Optional<String> newEmail,
                        Optional<String> newFirstName,
                        Optional<String> newSecondName)
            throws UserNotFoundException,
            DuplicateEmailException;
}
