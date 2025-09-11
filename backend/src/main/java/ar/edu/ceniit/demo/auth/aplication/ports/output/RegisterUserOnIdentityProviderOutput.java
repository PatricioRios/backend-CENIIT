package ar.edu.ceniit.demo.auth.aplication.ports.output;

import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.UserNameIsAlreadyInUse;
import ar.edu.ceniit.demo.auth.aplication.ports.output.dto.AuthUserResponse;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.DuplicateEmailException;

public interface RegisterUserOnIdentityProviderOutput {
    AuthUserResponse registerUserOnIdentityProvider(AuthUserResponse user) throws
            UserNameIsAlreadyInUse,
            DuplicateEmailException;
}
