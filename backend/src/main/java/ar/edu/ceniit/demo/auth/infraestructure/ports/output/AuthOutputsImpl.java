package ar.edu.ceniit.demo.auth.infraestructure.ports.output;

import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.AuthException;
import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.Conflicts;
import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.IdentityProviderException;
import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.UserNotFoundInProviderException;
import ar.edu.ceniit.demo.auth.aplication.ports.input.dto.UserForDomain;
import ar.edu.ceniit.demo.auth.aplication.ports.output.AuthOutputs;
import ar.edu.ceniit.demo.auth.aplication.ports.output.CreateUserOnDomainOutput;
import ar.edu.ceniit.demo.auth.aplication.ports.output.dto.AuthUserResponse;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;
import ar.edu.ceniit.demo.user.aplication.ports.input.CreateUserUseCase;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;

@Component
public class AuthOutputsImpl implements AuthOutputs/*, CreateUserOnDomainOutput */{

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
    public void deleteUserOnIdentityProvider(UUID userId) throws AuthException {
        UsersResource usersResource = realmResource.users();
        try {
            logger.debug("Attempting to delete user with ID: {}", userId);
            usersResource.delete(userId.toString());
            logger.info("Successfully deleted user with ID: {} from Keycloak", userId);
        } catch (NotFoundException e) {
            logger.warn("User with ID {} not found in Keycloak. Assuming already deleted.", userId);
            throw new UserNotFoundInProviderException("User with ID " + userId + " not found in the identity provider.", e);
        } catch (Exception e) {
            logger.error("Error deleting user with ID {} from Keycloak", userId, e);
            throw new IdentityProviderException("An unexpected error occurred while deleting user " + userId, e);
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
/*
    @Override
    public void createUser(UserForDomain user) throws AuthException {
        User userToCreate = new User();
        userToCreate.setUuid(user.getUuid());
        userToCreate.setUsername(user.getUsername());
        userToCreate.setEmail(user.getEmail());
        userToCreate.setFirstName(user.getFirstName());
        userToCreate.setLastName(user.getLastName());
        userToCreate.setPassword(user.getPassword());

        try {
            createUserUseCase.createUser(userToCreate);
        } catch (Exception e) {
            throw new AuthException("Error creating user in local database", e);
        }
    }

 */
}
