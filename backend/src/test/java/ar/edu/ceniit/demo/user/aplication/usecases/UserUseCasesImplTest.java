package ar.edu.ceniit.demo.user.aplication.usecases;

import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.BadRequestOnCreateUserException;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.BadRequestOnUpdateUserException;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserBadRequestException;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserNotFoundException;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.GetUserByUUIDResponse;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.UpdateUserDTO;
import ar.edu.ceniit.demo.user.aplication.ports.output.OutputsUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserUseCasesImplTest {

    @Mock
    private OutputsUser userOutputs;

    private UserUseCasesImpl userUseCases;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userUseCases = new UserUseCasesImpl(userOutputs);
    }

    private User createValidUser() {
        return User.builder()
                .username("testuser")
                .email("test@example.com")
                .firstName("Test")
                .lastName("User")
                .dni(12345678)
                .secondName(Optional.empty())
                .secondLastName(Optional.empty())
                .build();
    }

    // Tests para createUser
    @Test
    @DisplayName("createUser_whenUserIsValid_shouldReturnCreatedUser")
    void createUser_whenUserIsValid_shouldReturnCreatedUser() throws Exception {
        // Given
        User userToCreate = createValidUser();
        when(userOutputs.createUser(userToCreate)).thenReturn(userToCreate);

        // When
        User createdUser = userUseCases.createUser(userToCreate);

        // Then
        assertNotNull(createdUser);
        assertEquals("testuser", createdUser.getUsername());
        verify(userOutputs, times(1)).createUser(userToCreate);
    }

    @Test
    @DisplayName("createUser_whenUsernameIsNull_shouldThrowBadRequestException")
    void createUser_whenUsernameIsNull_shouldThrowBadRequestException() throws Exception {
        // Given
        User userWithNullUsername = User.builder().email("test@example.com").firstName("Test").lastName("User").build();

        // When & Then
        BadRequestOnCreateUserException exception = assertThrows(BadRequestOnCreateUserException.class, () -> {
            userUseCases.createUser(userWithNullUsername);
        });
        assertEquals(BadRequestOnCreateUserException.Reason.INVALID_USERNAME, exception.getReason());
        verify(userOutputs, never()).createUser(any());
    }

    @Test
    @DisplayName("createUser_whenEmailIsInvalid_shouldThrowBadRequestException")
    void createUser_whenEmailIsInvalid_shouldThrowBadRequestException() throws Exception {
        // Given
        User userWithInvalidEmail = User.builder()
                .username("testuser")
                .email("invalid-email")
                .firstName("Test")
                .lastName("User")
                .build();

        // When & Then
        BadRequestOnCreateUserException exception = assertThrows(BadRequestOnCreateUserException.class, () -> {
            userUseCases.createUser(userWithInvalidEmail);
        });
        assertEquals(BadRequestOnCreateUserException.Reason.INVALID_EMAIL, exception.getReason());
        verify(userOutputs, never()).createUser(any());
    }

    // Tests para deleteUser
    @Test
    @DisplayName("deleteUser_whenUuidIsValid_shouldCallOutput")
    void deleteUser_whenUuidIsValid_shouldCallOutput() throws Exception {
        // Given
        UUID userId = UUID.randomUUID();
        doNothing().when(userOutputs).deleteUserByUUID(userId);

        // When
        assertDoesNotThrow(() -> userUseCases.deleteUser(userId));

        // Then
        verify(userOutputs, times(1)).deleteUserByUUID(userId);
    }

    @Test
    @DisplayName("deleteUser_whenUuidIsNull_shouldThrowBadRequestException")
    void deleteUser_whenUuidIsNull_shouldThrowBadRequestException() throws Exception {
        // When & Then
        assertThrows(UserBadRequestException.class, () -> userUseCases.deleteUser(null));
        verify(userOutputs, never()).deleteUserByUUID(any());
    }

    // Tests para updateUser
    @Test
    @DisplayName("updateUser_whenDtoIsValid_shouldReturnUpdatedDto")
    void updateUser_whenDtoIsValid_shouldReturnUpdatedDto() throws Exception {
        // Given
        UUID userId = UUID.randomUUID();
        UpdateUserDTO updateDto = new UpdateUserDTO(userId, Optional.of("new@example.com"), Optional.of("NewName"), Optional.empty(), Optional.of("NewLastName"), Optional.empty());
        when(userOutputs.updateUser(updateDto)).thenReturn(updateDto);

        // When
        UpdateUserDTO updatedDto = userUseCases.updateUser(updateDto);

        // Then
        assertNotNull(updatedDto);
        assertEquals("new@example.com", updatedDto.getEmail().orElse(null));
        verify(userOutputs, times(1)).updateUser(updateDto);
    }

    @Test
    @DisplayName("updateUser_whenDtoIsInvalid_shouldThrowBadRequestException")
    void updateUser_whenDtoIsInvalid_shouldThrowBadRequestException() throws Exception {
        // Given
        UpdateUserDTO invalidDto = new UpdateUserDTO(UUID.randomUUID(), Optional.of(" "), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());

        // When & Then
        assertThrows(BadRequestOnUpdateUserException.class, () -> userUseCases.updateUser(invalidDto));
        verify(userOutputs, never()).updateUser(any());
    }

    // Tests para getByUUID
    @Test
    @DisplayName("getByUUID_whenUserExists_shouldReturnUserResponse")
    void getByUUID_whenUserExists_shouldReturnUserResponse() throws Exception {
        // Given
        UUID userId = UUID.randomUUID();
        GetUserByUUIDResponse response = new GetUserByUUIDResponse(userId, 1, "testuser", "test@example.com", "Test", null, "User", null, 12345678, Instant.now(), Instant.now(), Set.of("user"));
        when(userOutputs.getUserByUUID(userId)).thenReturn(response);

        // When
        GetUserByUUIDResponse foundUser = userUseCases.getByUUID(userId);

        // Then
        assertNotNull(foundUser);
        assertEquals(userId, foundUser.uuid());
        assertEquals("testuser", foundUser.username());
        verify(userOutputs, times(1)).getUserByUUID(userId);
    }

    @Test
    @DisplayName("getByUUID_whenUserDoesNotExist_shouldThrowNotFoundException")
    void getByUUID_whenUserDoesNotExist_shouldThrowNotFoundException() throws Exception {
        // Given
        UUID userId = UUID.randomUUID();
        when(userOutputs.getUserByUUID(userId)).thenReturn(null);

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userUseCases.getByUUID(userId));
        verify(userOutputs, times(1)).getUserByUUID(userId);
    }

    @Test
    @DisplayName("getByUUID_whenUuidIsNull_shouldThrowBadRequestException")
    void getByUUID_whenUuidIsNull_shouldThrowBadRequestException() throws Exception {
        // When & Then
        assertThrows(UserBadRequestException.class, () -> userUseCases.getByUUID(null));
        verify(userOutputs, never()).getUserByUUID(any());
    }
}
