package ar.edu.ceniit.demo.auth.aplication.ports.output;



import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.UserNotFoundInProviderException;

import java.util.Set;
import java.util.UUID;

public interface PutRolesToUserOutput {
    void putRolesToUser(UUID userId, Set<String> roles) throws UserNotFoundInProviderException;
}