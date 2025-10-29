package ar.edu.ceniit.demo.auth.aplication.ports.input;

import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.BadRequestOnRegisterUserException;
import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.DuplicatedEmailException;
import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.UserNameIsAlreadyInUse;
import ar.edu.ceniit.demo.auth.aplication.ports.input.dto.RegisterUserCommand;
import java.util.UUID;

public interface RegisterNewUserUseCase {
    UUID register(RegisterUserCommand command) throws
            BadRequestOnRegisterUserException,
            UserNameIsAlreadyInUse,
            DuplicatedEmailException;

}
