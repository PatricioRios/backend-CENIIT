package ar.edu.ceniit.demo.auth.aplication.ports.input;

import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.DuplicatedEmailException;
import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.UserNotFoundInProviderException;

import java.util.Optional;
import java.util.UUID;

public interface UpdateBasicInfoUserUseCase {
    void updateBasicInfoOnUser(UUID uuidOnIP,
                        Optional<String> newEmail,
                        Optional<String> newFirstName,
                        Optional<String> newSecondName)
            throws UserNotFoundInProviderException,
            DuplicatedEmailException;
}
