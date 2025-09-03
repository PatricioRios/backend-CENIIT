package ar.edu.ceniit.demo.user.aplication.usecases;

import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.BadRequest;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.IdCannotBeNull;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserBaseException;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserNotFoundException;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;
import ar.edu.ceniit.demo.user.aplication.ports.output.OutputsUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserUseCasesImplTest {
    @Mock
    private OutputsUser userOutputs;

    @InjectMocks
    private UserUseCasesImpl userUseCases;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Prueba la creación exitosa de un usuario.
     * @throws UserBaseException si ocurre un error base de usuario.
     */
    @Test
    void createUser_Success() throws UserBaseException {
        User user = User.builder().username("testuser").password("Password123").build();
        when(userOutputs.createUser(any(User.class))).thenReturn(user);

        User createdUser = userUseCases.createUser(user);

        assertNotNull(createdUser);
        assertEquals("testuser", createdUser.getUsername());
        verify(userOutputs, times(1)).createUser(user);
    }

    /**
     * Prueba que se lance una excepción BadRequest cuando el nombre de usuario es nulo.
     */
    @Test
    void createUser_NullUsername_ThrowsBadRequest() {
        User user = User.builder().password("Password123").build();
        assertThrows(BadRequest.class, () -> userUseCases.createUser(user));
    }

    /**
     * Prueba que se lance una excepción BadRequest cuando el nombre de usuario está vacío.
     */
    @Test
    void createUser_EmptyUsername_ThrowsBadRequest() {
        User user = User.builder().username("").password("Password123").build();
        assertThrows(BadRequest.class, () -> userUseCases.createUser(user));
    }

    /**
     * Prueba que se lance una excepción BadRequest cuando la contraseña es nula.
     */
    @Test
    void createUser_NullPassword_ThrowsBadRequest() {
        User user = User.builder().username("testuser").build();
        assertThrows(BadRequest.class, () -> userUseCases.createUser(user));
    }

    /**
     * Prueba que se lance una excepción BadRequest cuando la contraseña es inválida.
     */
    @Test
    void createUser_InvalidPassword_ThrowsBadRequest() {
        User user = User.builder().username("testuser").password("pass").build();
        assertThrows(BadRequest.class, () -> userUseCases.createUser(user));
    }

    /**
     * Prueba la eliminación exitosa de un usuario.
     * @throws IdCannotBeNull si el ID es nulo.
     * @throws UserNotFoundException si el usuario no se encuentra.
     */
    @Test
    void deleteUser_Success() throws IdCannotBeNull, UserNotFoundException {
        UUID userId = UUID.randomUUID();
        doNothing().when(userOutputs).deleteUserByUUID(userId);
        userUseCases.deleteUser(userId);
        verify(userOutputs, times(1)).deleteUserByUUID(userId);
    }

    /**
     * Prueba que se lance una excepción IdCannotBeNull cuando se intenta eliminar un usuario con un ID nulo.
     */
    @Test
    void deleteUser_NullId_ThrowsIdCannotBeNull() {
        assertThrows(IdCannotBeNull.class, () -> userUseCases.deleteUser(null));
    }

    /**
     * Prueba la actualización exitosa de un usuario.
     * @throws UserBaseException si ocurre un error base de usuario.
     */
    @Test
    void updateUser_Success() throws UserBaseException {
        UUID userId = UUID.randomUUID();
        User user = User.builder().uuid(userId).username("testuser").password("Password123").build();
        doNothing().when(userOutputs).updateUser(any(User.class));

        User updatedUser = userUseCases.updateUser(user);

        assertNotNull(updatedUser);
        verify(userOutputs, times(1)).updateUser(user);
    }

    /**
     * Prueba la obtención exitosa de un usuario por su UUID.
     * @throws UserBaseException si ocurre un error base de usuario.
     */
    @Test
    void getByUUID_Success() throws UserBaseException {
        UUID userId = UUID.randomUUID();
        User user = User.builder().uuid(userId).username("testuser").password("Password123").build();
        when(userOutputs.getUserByUUID(userId)).thenReturn(user);

        User foundUser = userUseCases.getByUUID(userId, userId);

        assertNotNull(foundUser);
        assertEquals("", foundUser.getPassword()); // Password should be cleared
        verify(userOutputs, times(1)).getUserByUUID(userId);
    }

    /**
     * Prueba que se lance una excepción UserNotFoundException cuando se busca un usuario con un ID nulo.
     */
    @Test
    void getByUUID_NullId_ThrowsUserNotFoundException() {
        assertThrows(UserNotFoundException.class, () -> userUseCases.getByUUID(UUID.randomUUID(), null));
    }

    /**
     * Prueba que se lance una excepción UserNotFoundException cuando no se encuentra un usuario.
     */
    @Test
    void getByUUID_UserNotFound_ThrowsUserNotFoundException() {
        UUID userId = UUID.randomUUID();
        when(userOutputs.getUserByUUID(userId)).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> userUseCases.getByUUID(userId, userId));
    }
}