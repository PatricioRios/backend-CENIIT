package ar.edu.ceniit.demo.auth.aplication.ports.input;

import java.util.Set;
import java.util.UUID;

public interface PutRolesToUserUseCase {
    void putRolesToUser(UUID uuid, Set<String> roles) throws Exception;
}
