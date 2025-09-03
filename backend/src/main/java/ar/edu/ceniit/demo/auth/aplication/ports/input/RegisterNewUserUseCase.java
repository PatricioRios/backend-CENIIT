package ar.edu.ceniit.demo.auth.aplication.ports.input;

import ar.edu.ceniit.demo.auth.aplication.ports.input.dto.RegisterUserCommand;
import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.AuthException;

public interface RegisterNewUserUseCase {
    void register(RegisterUserCommand command) throws AuthException, Exception;
}
