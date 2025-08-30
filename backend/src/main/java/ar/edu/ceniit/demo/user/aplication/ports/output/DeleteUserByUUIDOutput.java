package ar.edu.ceniit.demo.user.aplication.ports.output;

import java.util.UUID;

public interface DeleteUserByUUIDOutput {
    void deleteUserByUUID(UUID uuid);
}