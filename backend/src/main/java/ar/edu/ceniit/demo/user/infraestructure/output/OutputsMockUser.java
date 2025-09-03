package ar.edu.ceniit.demo.user.infraestructure.output;

import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.AuthException;
import ar.edu.ceniit.demo.auth.aplication.ports.input.dto.UserForDomain;
import ar.edu.ceniit.demo.auth.aplication.ports.output.CreateUserOnDomainOutput;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;
import ar.edu.ceniit.demo.user.aplication.ports.output.OutputsUser;
import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
@Primary
public class OutputsMockUser implements OutputsUser, CreateUserOnDomainOutput {

    private final Map<UUID, User> users = new HashMap<>();

    @Override
    public User createUser(User user) {
        user.setUuid(UUID.randomUUID());
        users.put(user.getUuid(), user);
        return user;
    }

    @Override
    public void deleteUserByUUID(UUID uuid) {
        users.remove(uuid);
    }

    @Override
    public User getUserByUUID(UUID uuid) {
        return users.get(uuid);
    }

    @Override
    public void updateUser(User user) {
        users.put(user.getUuid(), user);
    }

    @Override
    public void createUser(UserForDomain user) throws AuthException {
        User newUser = User.builder()
                .uuid(user.getUuid())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
        users.put(newUser.getUuid(), newUser);
    }
}