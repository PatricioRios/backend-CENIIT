package ar.edu.ceniit.demo.auth.aplication.ports.output;

import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.AuthException;
import ar.edu.ceniit.demo.auth.aplication.ports.input.dto.UserForDomain;

public interface CreateUserOnDomainOutput {
    void createUser(UserForDomain user) throws AuthException;
}
