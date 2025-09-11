package ar.edu.ceniit.demo.auth.infraestructure.ports.input;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.BadRequestOnRegisterUserException;
import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.DuplicatedEmailException;
import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.UserNameIsAlreadyInUse;
import ar.edu.ceniit.demo.auth.aplication.ports.input.AuthUseCases;
import ar.edu.ceniit.demo.auth.infraestructure.ports.input.dto.PutRolesOnUserRequest;
import ar.edu.ceniit.demo.auth.infraestructure.ports.input.dto.PutRolesOnUserResponse;
import ar.edu.ceniit.demo.auth.infraestructure.ports.input.dto.RegisterUserRequest;
import ar.edu.ceniit.demo.auth.infraestructure.ports.input.mapper.AuthMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthUseCases authUseCases;
    private final AuthMapper authMapper;

    public AuthController(AuthUseCases authUseCases) {
        this.authMapper = new AuthMapper();
        this.authUseCases = authUseCases;
    }

    @PreAuthorize("hasRole('put-roles-on-user')")
    @PutMapping("/roles")
    public ResponseEntity<PutRolesOnUserResponse> setRolesOnUser(@Valid @RequestBody PutRolesOnUserRequest request) throws Exception{
        authUseCases.putRolesToUser(UUID.fromString(request.getUuid()), request.getRoles());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PreAuthorize("hasRole('list-roles')")
    @GetMapping("/roles")
    public ResponseEntity<Set<String>> getAllRoles() throws Exception {
            return ResponseEntity.ok(authUseCases.getAllRoles());
    }
    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = """
                    - Bad request on register user (e.g. invalid fields)
                    - User name is already in use
                    - Email is already in use
                    """, content = @Content(mediaType = "text/plain"))
    })
    @PreAuthorize("hasRole('backend-admin')")
    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterUserRequest request) throws BadRequestOnRegisterUserException,
            UserNameIsAlreadyInUse,
            DuplicatedEmailException {

        authUseCases.register(authMapper.toDomain(request));
        //try{
        //
        //    authUseCases.register(authMapper.toDomain(request));
        //} catch (BadRequestOnRegisterUserException e) {
        //    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        //} catch (UserNameIsAlreadyInUse e) {
        //    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        //} catch (DuplicateEmailException e) {
        //    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        //    throw new RuntimeException(e);
        //}

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}