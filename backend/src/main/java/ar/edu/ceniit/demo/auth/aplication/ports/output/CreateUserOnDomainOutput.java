package ar.edu.ceniit.demo.auth.aplication.ports.output;

import ar.edu.ceniit.demo.auth.aplication.ports.input.dto.UserForDomain;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.DuplicateEmailException;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserNameIsAlreadyInUse;

public interface CreateUserOnDomainOutput {
    void createUser(UserForDomain user) throws UserNameIsAlreadyInUse, DuplicateEmailException;
}
