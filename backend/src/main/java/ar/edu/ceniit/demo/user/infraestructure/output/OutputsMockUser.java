package ar.edu.ceniit.demo.user.infraestructure.output;

import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.AuthException;
import ar.edu.ceniit.demo.auth.aplication.ports.input.dto.UserForDomain;
import ar.edu.ceniit.demo.auth.aplication.ports.output.CreateUserOnDomainOutput;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.SortOrder;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.Criteria;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.GetUserByUUIDResponse;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.UpdateUserDTO;
import ar.edu.ceniit.demo.user.aplication.ports.output.OutputsUser;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.*;

@Repository
@Primary
public class OutputsMockUser implements OutputsUser, CreateUserOnDomainOutput {

    private final Map<UUID, User> users = new HashMap<>();

    @Override
    public User createUser(User user) {
        user.setUuid(UUID.randomUUID());
        user.setId(users.size() + 1);
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        users.put(user.getUuid(), user);
        return user;
    }

    @Override
    public void deleteUserByUUID(UUID uuid) {
        users.remove(uuid);
    }

    @Override
    public GetUserByUUIDResponse getUserByUUID(UUID uuid) {
        User user = users.get(uuid);
        if (user == null) {
            return null;
        }
        // Usando el constructor correcto del record
        return new GetUserByUUIDResponse(
                user.getUuid(),
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getSecondName().orElse(null),
                user.getLastName(),
                user.getSecondLastName().orElse(null),
                user.getDni(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                Set.of("user") // Rol de ejemplo
        );
    }

    @Override
    public UpdateUserDTO updateUser(UpdateUserDTO userDTO) {
        User user = users.get(userDTO.getUuid());
        if (user != null) {
            userDTO.getEmail().ifPresent(user::setEmail);
            userDTO.getFirstName().ifPresent(user::setFirstName);
            userDTO.getLastName().ifPresent(user::setLastName);
            userDTO.getSecondName().ifPresent(s -> user.setSecondName(Optional.of(s)));
            userDTO.getSecondLastName().ifPresent(s -> user.setSecondLastName(Optional.of(s)));
            user.setUpdatedAt(Instant.now());
            users.put(user.getUuid(), user);
        }
        return userDTO;
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

    @Override
    public Set<User> getAllUsers(Criteria criteria, SortOrder sortOrder, int limit, int offset) {
        return Set.copyOf(users.values());
    }
}