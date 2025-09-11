package ar.edu.ceniit.demo.auth.infraestructure.ports.output;

import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.*;
import ar.edu.ceniit.demo.auth.aplication.ports.output.AuthOutputs;
import ar.edu.ceniit.demo.auth.aplication.ports.output.dto.AuthUserResponse;
import ar.edu.ceniit.demo.common.exceptions.FatalErrorException;
import ar.edu.ceniit.demo.common.exceptions.WarningErrorException;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserNotFoundException;
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
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AuthOutputsImpl implements AuthOutputs, IPOutputs/*, CreateUserOnDomainOutput */ {
    private final String CLIENT_ID = "ceniit-backend-develop";
    private static final Logger logger = LoggerFactory.getLogger(AuthOutputsImpl.class);
    private final Keycloak keycloakClient;
    private final RealmResource realmResource;

    public AuthOutputsImpl(Keycloak keycloakClient, RealmResource realmResource) {
        this.keycloakClient = keycloakClient;
        this.realmResource = realmResource;
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
                throw new WarningErrorException("Failed to delete user with ID " + userId + " from the identity provider. Status: " + response.getStatus());
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

    /**
     * Valida si un nombre de usuario o un email ya existen en Keycloak.
     * Si alguno de los dos ya existe, lanza una excepción específica.
     *
     * @param username El nombre de usuario a validar.
     * @param email El email a validar.
     * @throws UserNameIsAlreadyInUse Si el nombre de usuario ya está en uso.
     * @throws DuplicatedEmailException Si el email ya está en uso.
     */
    private void preValidateUser(String username, String email) throws UserNameIsAlreadyInUse, DuplicatedEmailException {
        UsersResource usersResource = realmResource.users();

        // Buscar por nombre de usuario
        List<UserRepresentation> usernameSearch = usersResource.searchByUsername(username, true);
        if (!usernameSearch.isEmpty()) {
            logger.warn("El nombre de usuario '{}' ya existe en Keycloak.", username);
            throw new UserNameIsAlreadyInUse("Un usuario con este nombre de usuario ya existe.");
        }

        // Buscar por email
        List<UserRepresentation> emailSearch = usersResource.searchByEmail(email, true);
        if (!emailSearch.isEmpty()) {
            logger.warn("El email '{}' ya existe en Keycloak.", email);
            throw new DuplicatedEmailException("Un usuario con este email ya existe.");
        }
    }

    @Override
    public AuthUserResponse registerUserOnIdentityProvider(AuthUserResponse user) throws
            UserNameIsAlreadyInUse,
            DuplicatedEmailException {

        // Paso 1: Realizar la pre-validación antes de intentar crear el usuario
        preValidateUser(user.getUsername(), user.getEmail());

        // Si la validación no lanza excepciones, procedemos a crear el usuario
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

        // Paso 2: Intentar la creación del usuario
        Response response = usersResource.create(userRepresentation);

        if (response.getStatus() == 201) {
            String path = response.getLocation().getPath();
            String userId = path.substring(path.lastIndexOf('/') + 1);
            user.setId(UUID.fromString(userId));

            logger.info("El usuario {} ha sido creado exitosamente en Keycloak con ID {}", user.getUsername(), userId);
            return user;
        } else {
            // El error 409 ya se ha manejado en la pre-validación,
            // por lo que este 'else' se ejecutará para otros errores
            String errorBody = "";
            try {
                if (response.hasEntity()) {
                    errorBody = response.readEntity(String.class);
                }
            } catch (Exception e) {
                logger.warn("No se pudo leer el cuerpo de la respuesta de error de Keycloak.", e);
            }
            logger.error("Error al crear el usuario {} en Keycloak. Estado: {}. Cuerpo: {}", user.getUsername(), response.getStatus(), errorBody);
            throw new WarningErrorException("Error al crear el usuario en Keycloak. Estado: " + response.getStatus() + ". Cuerpo: " + errorBody);
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
    public void putRolesToUser(UUID userId, Set<String> roles) throws UserNotFoundInProviderException {
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
                .orElseThrow(() -> new FatalErrorException("Client with ID " + this.CLIENT_ID + " not found."));
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
    public void updateBasicInfoOnUser(UUID uuidOnIP, Optional<String> newEmail, Optional<String> newFirstName, Optional<String> newSecondName) throws UserNotFoundInProviderException, DuplicatedEmailException {
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
                throw new DuplicatedEmailException("The email " + email + " is already in use by another user.");
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
        } catch (DuplicatedEmailException e) {
            throw new ar.edu.ceniit.demo.user.aplication.entitys.exceptions.DuplicateEmailException("The email is already in use by another user.", e);
        }
    }
}
