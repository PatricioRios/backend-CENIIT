package ar.edu.ceniit.demo.auth.infraestructure.ports.input;

import ar.edu.ceniit.demo.auth.aplication.ports.input.AuthUseCases;
import ar.edu.ceniit.demo.auth.aplication.ports.input.RegisterNewUserUseCase;
import ar.edu.ceniit.demo.auth.aplication.ports.input.dto.RegisterUserCommand;
import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.AuthException;
import ar.edu.ceniit.demo.auth.infraestructure.ports.input.dto.RegisterUserRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthUseCases registerNewUserUseCase;

    public AuthController(AuthUseCases registerNewUserUseCase) {
        this.registerNewUserUseCase = registerNewUserUseCase;
    }
    @PreAuthorize("hasRole('backend-admin')")
    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterUserRequest request) throws AuthException, Exception {
        RegisterUserCommand command = RegisterUserCommand.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();

        registerNewUserUseCase.register(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
