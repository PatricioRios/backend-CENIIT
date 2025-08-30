package ar.edu.ceniit.demo.user.aplication.ports.input;

import java.util.UUID;

public interface DeleteUserUseCase {
    void deleteUser(UUID uuid);
}
