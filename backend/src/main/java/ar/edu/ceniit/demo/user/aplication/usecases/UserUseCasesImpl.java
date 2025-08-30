package ar.edu.ceniit.demo.user.aplication.usecases;

import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;
import ar.edu.ceniit.demo.user.aplication.ports.input.UserUseCases;
import ar.edu.ceniit.demo.user.aplication.ports.output.CreateUserOutput;
import ar.edu.ceniit.demo.user.aplication.ports.output.UserOutputs;

import java.util.UUID;

public class UserUseCasesImpl implements UserUseCases {
    private final UserOutputs userOutputs;
    public UserUseCasesImpl(UserOutputs userOutputs) {
        this.userOutputs = userOutputs;
    }
    @Override
    public User createUser(User user) {
        return userOutputs.createUser(user);
    }

    @Override
    public void deleteUser(UUID uuid) {
        try {
            if (uuid == null) {
                throw new IllegalArgumentException("UUID no puede ser nulo");
            }
            userOutputs.deleteUserByUUID(uuid);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            return;
        }
    }
    @Override
    public User updateUser(User user) {

    }

    @Override
    public User getByUUID(String uuid) {
        return null;
    }
}
