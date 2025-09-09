package ar.edu.ceniit.demo.auth.infraestructure.ports.input;

import ar.edu.ceniit.demo.auth.aplication.ports.input.AuthUseCases;
import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.AuthException;
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
    @PreAuthorize("hasRole('backend-admin')")
    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterUserRequest request) throws AuthException, Exception {
        authUseCases.register(authMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
