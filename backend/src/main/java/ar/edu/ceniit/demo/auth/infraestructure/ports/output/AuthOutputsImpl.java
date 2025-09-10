package ar.edu.ceniit.demo.auth.infraestructure.ports.output;

import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.*;
import ar.edu.ceniit.demo.auth.aplication.ports.input.dto.UserForDomain;
import ar.edu.ceniit.demo.auth.aplication.ports.output.AuthOutputs;
import ar.edu.ceniit.demo.auth.aplication.ports.output.CreateUserOnDomainOutput;
import ar.edu.ceniit.demo.auth.aplication.ports.output.dto.AuthUserResponse;
import ar.edu.ceniit.demo.common.exceptions.FatalErrorException;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserNotFoundException;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;
import ar.edu.ceniit.demo.user.aplication.ports.input.CreateUserUseCase;
import ar.edu.ceniit.demo.user.aplication.ports.output.identity_provider.IPOutputs;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
public class AuthOutputsImpl implements AuthOutputs, IPOutputs/*, CreateUserOnDomainOutput */ {
    private final String CLIENT_ID = "ceniit-backend-develop";
    private static final Logger logger = LoggerFactory.getLogger(AuthOutputsImpl.class);
    private final Keycloak keycloakClient;
    private final RealmResource realmResource;
    private final CreateUserUseCase createUserUseCase;

    public AuthOutputsImpl(Keycloak keycloakClient, RealmResource realmResource, CreateUserUseCase createUserUseCase) {
        this.keycloakClient = keycloakClient;
        this.realmResource = realmResource;
        this.createUserUseCase = createUserUseCase;
    }

    @Override
    public void deleteUserOnIdentityProvider(UUID userId) throws UserNotFoundInProviderException {
        UsersResource usersResource = realmResource.users();
        try {
            System.out.println("Attempting to delete user with ID: {}" + userId);
            Response response = usersResource.delete(userId.toString());
            if (response.getStatus() == 204) {
                System.out.println("Received 204 No Content response from Keycloak for user ID: {}" + userId);
            } else if (response.getStatus() == 404) {
                System.out.println("Received 404 Not Found response from Keycloak for user ID: {}" + userId);
                throw new UserNotFoundInProviderException("User with ID " + userId + " not found in the identity provider.");
            } else {
                String errorBody = "";
                try {
                    if (response.hasEntity()) {
                        errorBody = response.readEntity(String.class);
                    }
                } catch (Exception e) {
                    System.out.println("Could not read error response body from Keycloak." + e);
                }
                System.out.println("Failed to delete user with ID {} from Keycloak. Status: {}. Body: {}" + userId + response.getStatus() + errorBody);
                throw new IdentityProviderException("Failed to delete user with ID " + userId + " from the identity provider. Status: " + response.getStatus());
            }
            logger.info("Successfully deleted user with ID: {} from Keycloak", userId);
        } catch (NotFoundException e) {
            logger.warn("User with ID {} not found in Keycloak. Assuming already deleted.", userId);
            throw new UserNotFoundInProviderException("User with ID " + userId + " not found in the identity provider.", e);
        } catch (Exception e) {
            logger.error("Error deleting user with ID {} from Keycloak", userId, e);
            throw new FatalErrorException("no se que paso " + e.getMessage(),
                    e,
                    FatalErrorException.FatalErrorType.UNDEFINED);
        }
    }

    @Override
    public AuthUserResponse registerUserOnIdentityProvider(AuthUserResponse user) throws AuthException {
        UsersResource usersResource = realmResource.users();
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(user.getPassword());
        credentialRepresentation.setTemporary(false);

        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));

        Response response = usersResource.create(userRepresentation);

        if (response.getStatus() == 201) {
            String path = response.getLocation().getPath();
            String userId = path.substring(path.lastIndexOf('/') + 1);
            user.setId(UUID.fromString(userId));

            logger.info("User {} created successfully in Keycloak with ID {}", user.getUsername(), userId);
            return user;
        } else if (response.getStatus() == 409) {
            logger.warn("User {} could not be created in Keycloak due to a conflict (409).", user.getUsername());
            throw new Conflicts("A user with the same username or email already exists.");
        } else {
            String errorBody = "";
            try {
                if (response.hasEntity()) {
                    errorBody = response.readEntity(String.class);
                }
            } catch (Exception e) {
                logger.warn("Could not read error response body from Keycloak.", e);
            }
            logger.error("Error creating user {} in Keycloak. Status: {}. Body: {}", user.getUsername(), response.getStatus(), errorBody);
            throw new IdentityProviderException("Error creating user in Keycloak. Status: " + response.getStatus());
        }
    }

    @Override
    public Set<String> getAllRoles() {
        ClientRepresentation client = realmResource.clients().findByClientId(this.CLIENT_ID).get(0);
        Set<String> roles = realmResource.clients().get(client.getId()).roles().list()
                .stream()
                .map(r -> {
                    System.out.println("================================");
                    System.out.println("Role found: " + r);
                    System.out.println("Clien Role: " + r.getClientRole());

                    return r.getName();
                })
                .collect(java.util.stream.Collectors.toSet());
        return roles;
    }

    @Override
    public void putRolesToUser(UUID userId, Set<String> roles) throws Exception {
        System.out.println("--- Starting putRolesToUser ---");
        System.out.println("userId: " + userId);
        System.out.println("roles to assign: " + roles);

        UsersResource usersResource = realmResource.users();
        org.keycloak.representations.idm.UserRepresentation userRepresentation;
        try {
            userRepresentation = usersResource.get(userId.toString()).toRepresentation();
            System.out.println("User found in Keycloak: " + userRepresentation.getUsername());
        } catch (NotFoundException e) {
            System.out.println("Error: User not found in Keycloak.");
            throw new UserNotFoundInProviderException("User with ID " + userId + " not found in the identity provider.");
        }

        System.out.println("Client ID to search for: " + this.CLIENT_ID);
        ClientRepresentation client = realmResource.clients().findByClientId(this.CLIENT_ID).stream()
                .findFirst()
                .orElseThrow(() -> new IdentityProviderException("Client with ID " + this.CLIENT_ID + " not found."));
        System.out.println("Client found: " + client.getClientId() + " (internal ID: " + client.getId() + ")");


        var roleRepresentations = roles.stream()
                .map(roleName -> {
                    System.out.println("Searching for role: " + roleName);
                    try {
                        var roleRep = realmResource.clients().get(client.getId()).roles().get(roleName).toRepresentation();
                        System.out.println("Found role: " + roleRep.getName());
                        return roleRep;
                    } catch (NotFoundException e) {
                        logger.warn("Role '{}' not found for client '{}'", roleName, this.CLIENT_ID);
                        System.out.println("Warning: Role '" + roleName + "' not found for client '" + this.CLIENT_ID + "'");
                        return null;
                    }
                })
                .filter(java.util.Objects::nonNull)
                .collect(java.util.stream.Collectors.toList());

        System.out.println("Found " + roleRepresentations.size() + " role representations out of " + roles.size() + " requested roles.");
        roleRepresentations.forEach(r -> System.out.println("Role to be assigned: " + r.getName()));


        if (roleRepresentations.isEmpty() && !roles.isEmpty()) {
            logger.warn("None of the specified roles {} were found for client {}", roles, this.CLIENT_ID);
            System.out.println("Warning: None of the specified roles were found. Aborting role assignment.");
            return;
        }

        if (!roleRepresentations.isEmpty()) {
            System.out.println("Assigning roles to user...");
            usersResource.get(userId.toString()).roles().clientLevel(client.getId()).add(roleRepresentations);
            System.out.println("Role assignment call finished.");
            logger.info("Assigned client roles {} to user ID {} for client {} in Keycloak",
                    roleRepresentations.stream().map(r -> r.getName()).collect(java.util.stream.Collectors.toSet()),
                    userId, this.CLIENT_ID);
        }
        System.out.println("--- Finished putRolesToUser ---");
    }

    @Override
    public void updateBasicInfoOnUser(UUID uuidOnIP, Optional<String> newEmail, Optional<String> newFirstName, Optional<String> newSecondName) throws UserNotFoundInProviderException, DuplicateEmailException {
        UsersResource usersResource = realmResource.users();
        UserRepresentation userRepresentation;
        try {
            userRepresentation = usersResource.get(uuidOnIP.toString()).toRepresentation();
        } catch (NotFoundException e) {
            throw new UserNotFoundInProviderException("User with ID " + uuidOnIP + " not found in the identity provider.", e);
        }

        newEmail.ifPresent(email -> {
            // Verificar si el nuevo email ya está en uso por otro usuario
            var usersWithEmail = usersResource.search(null, null, null, email, 0, 2);
            boolean emailInUse = usersWithEmail.stream()
                    .anyMatch(u -> !u.getId().equals(uuidOnIP.toString()));
            if (emailInUse) {
                throw new DuplicateEmailException("The email " + email + " is already in use by another user.");
            }
            userRepresentation.setEmail(email);
        });

        newFirstName.ifPresent(userRepresentation::setFirstName);
        newSecondName.ifPresent(userRepresentation::setLastName);

        usersResource.get(uuidOnIP.toString()).update(userRepresentation);
    }

    @Override
    public void deleteUser(UUID uuidOnIP) throws UserNotFoundException {
        try {
            deleteUserOnIdentityProvider(uuidOnIP);
        } catch (UserNotFoundInProviderException e) {
            throw new UserNotFoundException("User with ID " + uuidOnIP + " not found in the identity provider.", e);
        }
    }

    @Override
    public void updateUserOnIP(UUID uuidOnIP,
                               Optional<String> newEmail,
                               Optional<String> newFirstName,
                               Optional<String> newSecondName) throws
            UserNotFoundException,
            ar.edu.ceniit.demo.user.aplication.entitys.exceptions.DuplicateEmailException {
        try {
            updateBasicInfoOnUser(uuidOnIP, newEmail, newFirstName, newSecondName);
        } catch (UserNotFoundInProviderException e) {
            throw new UserNotFoundException("User with ID " + uuidOnIP + " not found in the identity provider.", e);
        } catch (DuplicateEmailException e) {
            throw new ar.edu.ceniit.demo.user.aplication.entitys.exceptions.DuplicateEmailException("The email is already in use by another user.", e);
        }
    }
}
