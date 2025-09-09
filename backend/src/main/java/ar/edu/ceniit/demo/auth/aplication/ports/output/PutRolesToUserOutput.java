package ar.edu.ceniit.demo.auth.aplication.ports.output;

import java.util.Set;
import java.util.UUID;

public interface PutRolesToUserOutput {
    void putRolesToUser(UUID userId, Set<String> roles) throws Exception;
}