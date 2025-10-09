package ar.edu.ceniit.demo.user.aplication.usecases;

import ar.edu.ceniit.demo.common.exceptions.FatalErrorException;
import ar.edu.ceniit.demo.common.exceptions.WarningErrorException;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.*;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.GetUserByUUIDResponse;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.UpdateUserRequestDTO;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.UpdateUserResponseDTO;
import ar.edu.ceniit.demo.user.aplication.ports.output.UserOutputs;
import ar.edu.ceniit.demo.user.aplication.ports.output.identity_provider.IPOutputs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.PagedResult;

class UserUseCasesImplTest {

    @Mock
    private UserOutputs userOutputs;

    @Mock
    private IPOutputs ipOutputs;

    @InjectMocks
    private UserUseCasesImpl userUseCases;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenCreateUserWithValidData_shouldReturnUser() throws Exception {
        User user = User.builder()
                .username("testuser")
                .email("test@example.com")
                .firstName("Test")
                .secondName(Optional.empty())
                .lastName("User")
                .secondLastName(Optional.empty())
                .dni(12345678)
                .build();
        when(userOutputs.createUser(any(User.class))).thenReturn(user);

        User createdUser = userUseCases.createUser(user);

        assertNotNull(createdUser);
        assertEquals("testuser", createdUser.getUsername());
    }

    @Test
    void whenCreateUserWithNullUsername_shouldThrowBadRequest() {
        User user = User.builder()
                .username(null)
                .email("test@example.com")
                .firstName("Test")
                .secondName(Optional.empty())
                .lastName("User")
                .secondLastName(Optional.empty())
                .dni(12345678)
                .build();

        assertThrows(BadRequestOnCreateUserException.class, () -> {
            userUseCases.createUser(user);
        });
    }

    @Test
    void whenCreateUserWithEmptyUsername_shouldThrowBadRequest() {
        User user = User.builder()
                .username("")
                .email("test@example.com")
                .firstName("Test")
                .secondName(Optional.empty())
                .lastName("User")
                .secondLastName(Optional.empty())
                .dni(12345678)
                .build();

        assertThrows(BadRequestOnCreateUserException.class, () -> {
            userUseCases.createUser(user);
        });
    }

    @Test
    void whenCreateUserWithInvalidEmail_shouldThrowBadRequest() {
        User user = User.builder()
                .username("testuser")
                .email("invalid-email")
                .firstName("Test")
                .secondName(Optional.empty())
                .lastName("User")
                .secondLastName(Optional.empty())
                .dni(12345678)
                .build();

        assertThrows(BadRequestOnCreateUserException.class, () -> {
            userUseCases.createUser(user);
        });
    }

    @Test
    void whenCreateUserWithDuplicateUsername_shouldThrowUserNameIsAlreadyInUse() throws Exception {
        User user = User.builder()
                .username("testuser")
                .email("test@example.com")
                .firstName("Test")
                .secondName(Optional.empty())
                .lastName("User")
                .secondLastName(Optional.empty())
                .dni(12345678)
                .build();
        when(userOutputs.createUser(any(User.class))).thenThrow(new UserNameIsAlreadyInUse("testuser"));

        assertThrows(UserNameIsAlreadyInUse.class, () -> {
            userUseCases.createUser(user);
        });
    }

    @Test
    void whenCreateUserWithDuplicateEmail_shouldThrowDuplicateEmailException() throws Exception {
        User user = User.builder()
                .username("testuser")
                .email("test@example.com")
                .firstName("Test")
                .secondName(Optional.empty())
                .lastName("User")
                .secondLastName(Optional.empty())
                .dni(12345678)
                .build();
        when(userOutputs.createUser(any(User.class))).thenThrow(new DuplicateEmailException("test@example.com"));

        assertThrows(DuplicateEmailException.class, () -> {
            userUseCases.createUser(user);
        });
    }

    @Test
    void whenDeleteUserWithValidUUID_shouldDeleteUser() throws Exception {
        UUID uuid = UUID.randomUUID();
        GetUserByUUIDResponse userResponse = new GetUserByUUIDResponse(uuid, 1L, "testuser", "test@example.com", "Test", null, "User", null, 12345678, null, null, null);

        when(userOutputs.getUserByUUID(uuid)).thenReturn(userResponse);

        assertDoesNotThrow(() -> {
            userUseCases.deleteUser(uuid);
        });
    }

    @Test
    void whenDeleteUserWithNullUUID_shouldThrowUserBadRequestException() {
        assertThrows(UserBadRequestException.class, () -> {
            userUseCases.deleteUser(null);
        });
    }

    @Test
    void whenDeleteUserWithNonExistentUUID_shouldThrowUserNotFoundException() throws Exception {
        UUID uuid = UUID.randomUUID();

        when(userOutputs.getUserByUUID(uuid)).thenThrow(new UserNotFoundException(uuid.toString()));

        assertThrows(UserNotFoundException.class, () -> {
            userUseCases.deleteUser(uuid);
        });
    }

    @Test
    void whenDeleteUserFailsInIpProvider_shouldRollback() throws Exception {
        UUID uuid = UUID.randomUUID();
        GetUserByUUIDResponse userResponse = new GetUserByUUIDResponse(uuid, 1L, "testuser", "test@example.com", "Test", null, "User", null, 12345678, null, null, null);

        when(userOutputs.getUserByUUID(uuid)).thenReturn(userResponse);
        doThrow(new RuntimeException("IP provider error")).when(ipOutputs).deleteUser(uuid);

        assertThrows(RuntimeException.class, () -> {
            userUseCases.deleteUser(uuid);
        });

        verify(userOutputs, times(1)).createUser(any(User.class));
    }

    @Test
    void whenDeleteUserFailsInIpProviderAndRollbackFails_shouldThrowFatalErrorException() throws Exception {
        UUID uuid = UUID.randomUUID();
        GetUserByUUIDResponse userResponse = new GetUserByUUIDResponse(uuid, 1L, "testuser", "test@example.com", "Test", null, "User", null, 12345678, null, null, null);

        when(userOutputs.getUserByUUID(uuid)).thenReturn(userResponse);
        doThrow(new RuntimeException("IP provider error")).when(ipOutputs).deleteUser(uuid);
        when(userOutputs.createUser(any(User.class))).thenThrow(new RuntimeException("Rollback failed"));

        assertThrows(FatalErrorException.class, () -> {
            userUseCases.deleteUser(uuid);
        });
    }

    @Test
    void whenUpdateUserWithValidData_shouldUpdateUser() throws Exception {
        UUID uuid = UUID.randomUUID();
        UpdateUserRequestDTO request = new UpdateUserRequestDTO(uuid, Optional.of("new@example.com"), Optional.of("NewName"), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        UpdateUserResponseDTO response = new UpdateUserResponseDTO(uuid, Optional.of("new@example.com"), Optional.of("NewName"), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of("testuser"));
        GetUserByUUIDResponse beforeUpdate = new GetUserByUUIDResponse(uuid, 1L, "testuser", "test@example.com", "Test", null, "User", null, 12345678, null, null, null);

        when(userOutputs.getUserByUUID(uuid)).thenReturn(beforeUpdate);
        when(userOutputs.updateUser(request)).thenReturn(response);

        UpdateUserResponseDTO result = userUseCases.updateUser(request);

        assertNotNull(result);
        assertEquals(Optional.of("new@example.com"), result.getEmail());
    }

    @Test
    void whenUpdateUserWithNonExistentUUID_shouldThrowUserNotFoundException() throws Exception {
        UUID uuid = UUID.randomUUID();
        UpdateUserRequestDTO request = new UpdateUserRequestDTO(uuid, Optional.of("new@example.com"), Optional.of("NewName"), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());

        when(userOutputs.getUserByUUID(uuid)).thenThrow(new UserNotFoundException(uuid.toString()));

        assertThrows(UserNotFoundException.class, () -> {
            userUseCases.updateUser(request);
        });
    }

    @Test
    void whenUpdateUserWithInvalidData_shouldThrowBadRequestOnUpdateUserException() {
        UUID uuid = UUID.randomUUID();
        UpdateUserRequestDTO request = new UpdateUserRequestDTO(uuid, Optional.of("invalid-email"), Optional.of("NewName"), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());

        assertThrows(BadRequestOnUpdateUserException.class, () -> {
            userUseCases.updateUser(request);
        });
    }

    @Test
    void whenUpdateUserFailsInDomainDB_shouldRollbackInIpProvider() throws Exception {
        UUID uuid = UUID.randomUUID();
        UpdateUserRequestDTO request = new UpdateUserRequestDTO(uuid, Optional.of("new@example.com"), Optional.of("NewName"), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        GetUserByUUIDResponse beforeUpdate = new GetUserByUUIDResponse(uuid, 1L, "testuser", "test@example.com", "Test", null, "User", null, 12345678, null, null, null);

        when(userOutputs.getUserByUUID(uuid)).thenReturn(beforeUpdate);
        when(userOutputs.updateUser(request)).thenThrow(new UserNotFoundException(uuid.toString()));

        assertThrows(WarningErrorException.class, () -> {
            userUseCases.updateUser(request);
        });

        verify(ipOutputs, times(1)).updateUserOnIP(uuid, Optional.of(beforeUpdate.email()), Optional.of(beforeUpdate.firstName()), Optional.ofNullable(beforeUpdate.secondName()));
    }

    @Test
    void whenUpdateUserFailsInDomainDBAndRollbackFails_shouldThrowFatalErrorException() throws Exception {
        UUID uuid = UUID.randomUUID();
        UpdateUserRequestDTO request = new UpdateUserRequestDTO(uuid, Optional.of("new@example.com"), Optional.of("NewName"), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        GetUserByUUIDResponse beforeUpdate = new GetUserByUUIDResponse(uuid, 1L, "testuser", "test@example.com", "Test", null, "User", null, 12345678, null, null, null);

        when(userOutputs.getUserByUUID(uuid)).thenReturn(beforeUpdate);
        when(userOutputs.updateUser(request)).thenThrow(new UserNotFoundException(uuid.toString()));
        doThrow(new RuntimeException("IP provider error")).when(ipOutputs).updateUserOnIP(uuid, Optional.of(beforeUpdate.email()), Optional.of(beforeUpdate.firstName()), Optional.ofNullable(beforeUpdate.secondName()));

        assertThrows(WarningErrorException.class, () -> {
            userUseCases.updateUser(request);
        });
    }

    @Test
    void whenGetByUUIDWithValidUUID_shouldReturnUser() throws Exception {
        UUID uuid = UUID.randomUUID();
        GetUserByUUIDResponse response = new GetUserByUUIDResponse(uuid, 1L, "testuser", "test@example.com", "Test", null, "User", null, 12345678, null, null, null);

        when(userOutputs.getUserByUUID(uuid)).thenReturn(response);

        GetUserByUUIDResponse result = userUseCases.getByUUID(uuid);

        assertNotNull(result);
        assertEquals(uuid, result.uuid());
    }

    @Test
    void whenGetByUUIDWithNonExistentUUID_shouldThrowUserNotFoundException() throws Exception {
        UUID uuid = UUID.randomUUID();

        when(userOutputs.getUserByUUID(uuid)).thenThrow(new UserNotFoundException(uuid.toString()));

        assertThrows(UserNotFoundException.class, () -> {
            userUseCases.getByUUID(uuid);
        });
    }

    @Test
    void whenGetByUUIDWithNullUUID_shouldThrowUserBadRequestException() {
        assertThrows(UserBadRequestException.class, () -> {
            userUseCases.getByUUID(null);
        });
    }

    @Test
    void whenGetAllUsers_shouldReturnPagedResultOfUsers() {
        List<User> users = new ArrayList<>();
        users.add(User.builder().username("test1").build());
        users.add(User.builder().username("test2").build());
        PagedResult<User> pagedResult = new PagedResult<>(users, 2, 0, 10, 2, true, true, false);

        when(userOutputs.getAllUsers(any(), any(), anyInt(), anyInt())).thenReturn(pagedResult);

        PagedResult<User> result = userUseCases.getAllUsers(null, null, 10, 0);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
    }

    @Test
    void whenGetAllUsersWithNoUsers_shouldReturnEmptyPagedResult() {
        PagedResult<User> pagedResult = new PagedResult<>(new ArrayList<>(), 0, 0, 10, 0, true, true, true);
        when(userOutputs.getAllUsers(any(), any(), anyInt(), anyInt())).thenReturn(pagedResult);

        PagedResult<User> result = userUseCases.getAllUsers(null, null, 10, 0);

        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
    }
}
