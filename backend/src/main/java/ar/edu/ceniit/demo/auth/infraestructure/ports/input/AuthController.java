package ar.edu.ceniit.demo.auth.infraestructure.ports.input;

import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.BadRequestOnRegisterUserException;
import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.DuplicatedEmailException;
import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.UserNameIsAlreadyInUse;
import ar.edu.ceniit.demo.auth.aplication.ports.input.AuthUseCases;
import ar.edu.ceniit.demo.auth.infraestructure.ports.input.dto.PutRolesOnUserRequest;
import ar.edu.ceniit.demo.auth.infraestructure.ports.input.dto.PutRolesOnUserResponse;
import ar.edu.ceniit.demo.auth.infraestructure.ports.input.dto.RegisterUserRequest;
import ar.edu.ceniit.demo.auth.infraestructure.ports.input.mapper.AuthMapper;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserBadRequestException;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserNotFoundException;
import ar.edu.ceniit.demo.user.aplication.ports.input.UserUseCases;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.GetUserByUUIDResponse;
import ar.edu.ceniit.demo.user.infraestructure.input.dto.UserResponseDTO;
import ar.edu.ceniit.demo.user.infraestructure.input.mapper.UserDTOMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and authorization management")
public class AuthController {
    private final AuthUseCases authUseCases;
    private final AuthMapper authMapper;
    private final UserUseCases userUseCases;
    private final UserDTOMapper userDTOMapper;

    public AuthController(AuthUseCases authUseCases, UserUseCases userUseCases, UserDTOMapper userDTOMapper) {
        this.authMapper = new AuthMapper();
        this.authUseCases = authUseCases;
        this.userUseCases = userUseCases;
        this.userDTOMapper = userDTOMapper;
    }

    @Operation(
        summary = "Assign roles to a user",
        description = "Assigns a set of roles to a specific user identified by UUID. Requires 'put-roles-on-user' role."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Roles successfully assigned to the user"
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Bad request - Invalid UUID format or other validation errors"
        ),
        @ApiResponse(
            responseCode = "401", 
            description = "Unauthorized - User is not authenticated"
        ),
        @ApiResponse(
            responseCode = "403", 
            description = "Forbidden - User lacks the required 'put-roles-on-user' role"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "User not found in provider"
        )
    })
    @PreAuthorize("hasRole('PUT_AUTH_ROLES')")
    @PutMapping("/roles")
    public ResponseEntity<PutRolesOnUserResponse> setRolesOnUser(@Valid @RequestBody PutRolesOnUserRequest request) throws Exception{
        authUseCases.putRolesToUser(UUID.fromString(request.getUuid()), request.getRoles());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @Operation(
        summary = "Get all available roles",
        description = "Retrieves a list of all available roles in the system. Requires 'list-roles' role."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Roles successfully retrieved",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Set.class))
        ),
        @ApiResponse(
            responseCode = "401", 
            description = "Unauthorized - User is not authenticated"
        ),
        @ApiResponse(
            responseCode = "403", 
            description = "Forbidden - User lacks the required 'list-roles' role"
        )
    })
    @PreAuthorize("hasRole('GET_AUTH_ROLES')")
    @GetMapping("/roles")
    public ResponseEntity<Set<String>> getAllRoles() throws Exception {
            return ResponseEntity.ok(authUseCases.getAllRoles());
    }
    
    @Operation(
        summary = "Register a new user",
        description = "Registers a new user in the system. Requires 'backend-admin' role."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "User registered successfully"
        ),
        @ApiResponse(
            responseCode = "400", 
            description = """
                Bad request - Validation errors:
                - Invalid request data
                - User name is already in use
                - Email is already in use
                """,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
        ),
        @ApiResponse(
            responseCode = "401", 
            description = "Unauthorized - User is not authenticated"
        ),
        @ApiResponse(
            responseCode = "403", 
            description = "Forbidden - User lacks the required 'backend-admin' role"
        ),
        @ApiResponse(
            responseCode = "409", 
            description = "Conflict - User name or email already in use"
        )
    })
    @PreAuthorize("hasRole('ADMIN_USUARIOS')")
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody RegisterUserRequest request) throws BadRequestOnRegisterUserException,
            UserNameIsAlreadyInUse,
            DuplicatedEmailException,
            UserNotFoundException,
            UserBadRequestException {

        UUID newUuid = authUseCases.register(authMapper.toDomain(request));

        GetUserByUUIDResponse response = userUseCases.getByUUID(newUuid);

        return ResponseEntity.status(HttpStatus.CREATED).body(userDTOMapper.toResponse(response));
    }

}