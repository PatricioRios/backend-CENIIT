package ar.edu.ceniit.demo.auth.aplication.ports.output;

import ar.edu.ceniit.demo.auth.aplication.ports.output.dto.AuthUserResponse;
import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.AuthException;

public interface RegisterUserOnIdentityProviderOutput {
    AuthUserResponse registerUserOnIdentityProvider(AuthUserResponse user) throws AuthException;
}
