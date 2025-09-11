package ar.edu.ceniit.demo.auth.aplication.ports.output;

import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.DuplicatedEmailException;
import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.UserNameIsAlreadyInUse;
import ar.edu.ceniit.demo.auth.aplication.ports.output.dto.AuthUserResponse;

public interface RegisterUserOnIdentityProviderOutput {
    AuthUserResponse registerUserOnIdentityProvider(AuthUserResponse user) throws
            UserNameIsAlreadyInUse,
            DuplicatedEmailException;
}
