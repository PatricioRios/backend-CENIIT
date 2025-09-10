package ar.edu.ceniit.demo.user.infraestructure.output;

import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.AuthException;
import ar.edu.ceniit.demo.auth.aplication.ports.input.dto.UserForDomain;
import ar.edu.ceniit.demo.auth.aplication.ports.output.CreateUserOnDomainOutput;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.DuplicateEmailException;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserNameIsAlreadyInUse;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserNotFoundException;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.SortOrder;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.Criteria;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.GetUserByUUIDResponse;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.UpdateUserRequestDTO;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.UpdateUserResponseDTO;
import ar.edu.ceniit.demo.user.aplication.ports.output.UserOutputs;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.*;

@Repository
public class OutputsMockUser implements UserOutputs, CreateUserOnDomainOutput {

    private final Map<UUID, User> users = new HashMap<>();

    @Override
    public User createUser(User user) throws UserNameIsAlreadyInUse, DuplicateEmailException {
        if (users.values().stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))) {
            throw new UserNameIsAlreadyInUse("Username " + user.getUsername() + " is already in use.");
        }
        if (users.values().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))) {
            throw new DuplicateEmailException("Email " + user.getEmail() + " is already in use.");
        }
        user.setUuid(UUID.randomUUID());
        user.setId(users.size() + 1L);
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        users.put(user.getUuid(), user);
        return user;
    }

    @Override
    public void deleteUserByUUID(UUID uuid) throws UserNotFoundException {
        if (!users.containsKey(uuid)) {
            throw new UserNotFoundException();
        }
        users.remove(uuid);
    }

    @Override
    public GetUserByUUIDResponse getUserByUUID(UUID uuid) throws UserNotFoundException {
        User user = users.get(uuid);
        if (user == null) {
            throw new UserNotFoundException();
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
    public UpdateUserResponseDTO updateUser(UpdateUserRequestDTO userDTO) throws UserNotFoundException, DuplicateEmailException {
        User user = users.get(userDTO.getUuid());
        if (user == null) {
            throw new UserNotFoundException();
        }

        userDTO.getEmail().ifPresent(email -> {
            if (users.values().stream().anyMatch(u -> u.getEmail().equals(email) && !u.getUuid().equals(userDTO.getUuid()))) {
                try {
                    throw new DuplicateEmailException("Email " + email + " is already in use.");
                } catch (DuplicateEmailException e) {
                    throw new RuntimeException(e);
                }
            }
            user.setEmail(email);
        });

        userDTO.getFirstName().ifPresent(user::setFirstName);
        userDTO.getLastName().ifPresent(user::setLastName);
        userDTO.getSecondName().ifPresent(s -> user.setSecondName(Optional.of(s)));
        userDTO.getSecondLastName().ifPresent(s -> user.setSecondLastName(Optional.of(s)));
        user.setUpdatedAt(Instant.now());
        users.put(user.getUuid(), user);
        return null; // Debería retornar un UpdateUserResponseDTO adecuado. aveces amo el null 😂
    }

    @Override
    public void createUser(UserForDomain user) throws AuthException, UserNameIsAlreadyInUse, DuplicateEmailException {
        if (users.values().stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))) {
            throw new UserNameIsAlreadyInUse("Username " + user.getUsername() + " is already in use.");
        }
        if (users.values().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))) {
            throw new DuplicateEmailException("Email " + user.getEmail() + " is already in use.");
        }
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