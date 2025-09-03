package ar.edu.ceniit.demo.auth.aplication.usecases;

import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.AuthException;
import ar.edu.ceniit.demo.auth.aplication.ports.input.dto.RegisterUserCommand;
import ar.edu.ceniit.demo.auth.aplication.ports.input.dto.UserForDomain;
import ar.edu.ceniit.demo.auth.aplication.ports.output.AuthOutputs;
import ar.edu.ceniit.demo.auth.aplication.ports.output.CreateUserOnDomainOutput;
import ar.edu.ceniit.demo.auth.aplication.ports.output.dto.AuthUserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthUseCasesImplTest {

    @Mock
    private AuthOutputs authOutputs;

    @Mock
    private CreateUserOnDomainOutput createUserOnDomainOutput;

    @InjectMocks
    private AuthUseCasesImpl authUseCases;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Prueba el caso de éxito del registro de un usuario.
     * Verifica que el usuario se registra en el proveedor de identidad y en la base de datos local.
     * @throws Exception si ocurre un error inesperado.
     */
    @Test
    void register_Success() throws Exception {
        // Given
        RegisterUserCommand command = new RegisterUserCommand("testuser", "test@example.com", "password", "Test", "User");
        AuthUserResponse authUserResponse = AuthUserResponse.builder()
                .id(UUID.randomUUID())
                .username("testuser")
                .email("test@example.com")
                .firstName("Test")
                .lastName("User")
                .build();

        when(authOutputs.registerUserOnIdentityProvider(any(AuthUserResponse.class))).thenReturn(authUserResponse);
        doNothing().when(createUserOnDomainOutput).createUser(any(UserForDomain.class));

        // When
        authUseCases.register(command);

        // Then
        verify(authOutputs, times(1)).registerUserOnIdentityProvider(any(AuthUserResponse.class));
        verify(createUserOnDomainOutput, times(1)).createUser(any(UserForDomain.class));
        verify(authOutputs, never()).deleteUserOnIdentityProvider(any(UUID.class));
    }

    /**
     * Prueba el caso de fallo en el registro cuando la creación en la base de datos local falla.
     * Verifica que se intente compensar eliminando el usuario del proveedor de identidad.
     * @throws Exception si ocurre un error inesperado.
     */
    @Test
    void register_Failure_CompensationSuccess() throws Exception {
        // Given
        UUID userId = UUID.randomUUID();
        RegisterUserCommand command = new RegisterUserCommand("testuser", "test@example.com", "password", "Test", "User");
        AuthUserResponse authUserResponse = AuthUserResponse.builder().id(userId).username("testuser").build();

        when(authOutputs.registerUserOnIdentityProvider(any(AuthUserResponse.class))).thenReturn(authUserResponse);
        doThrow(new AuthException("DB error")).when(createUserOnDomainOutput).createUser(any(UserForDomain.class));
        doNothing().when(authOutputs).deleteUserOnIdentityProvider(any(UUID.class));

        // When & Then
        assertThrows(AuthException.class, () -> authUseCases.register(command));

        verify(authOutputs, times(1)).registerUserOnIdentityProvider(any(AuthUserResponse.class));
        verify(createUserOnDomainOutput, times(1)).createUser(any(UserForDomain.class));
        verify(authOutputs, times(1)).deleteUserOnIdentityProvider(userId);
    }

    /**
     * Prueba el caso de fallo crítico en el registro cuando tanto la creación en la base de datos local como la compensación fallan.
     * Verifica que se lance una excepción crítica.
     * @throws Exception si ocurre un error inesperado.
     */
    @Test
    void register_Failure_CompensationFailure() throws Exception {
        // Given
        UUID userId = UUID.randomUUID();
        RegisterUserCommand command = new RegisterUserCommand("testuser", "test@example.com", "password", "Test", "User");
        AuthUserResponse authUserResponse = AuthUserResponse.builder().id(userId).username("testuser").build();

        when(authOutputs.registerUserOnIdentityProvider(any(AuthUserResponse.class))).thenReturn(authUserResponse);
        doThrow(new AuthException("DB error")).when(createUserOnDomainOutput).createUser(any(UserForDomain.class));
        doThrow(new AuthException("IdP delete error")).when(authOutputs).deleteUserOnIdentityProvider(any(UUID.class));

        // When & Then
        Exception exception = assertThrows(Exception.class, () -> authUseCases.register(command));
        assertTrue(exception.getMessage().contains("Falla crítica en la compensación"));

        verify(authOutputs, times(1)).registerUserOnIdentityProvider(any(AuthUserResponse.class));
        verify(createUserOnDomainOutput, times(1)).createUser(any(UserForDomain.class));
        verify(authOutputs, times(1)).deleteUserOnIdentityProvider(userId);
    }
}
