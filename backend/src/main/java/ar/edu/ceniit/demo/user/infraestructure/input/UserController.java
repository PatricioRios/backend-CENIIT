package ar.edu.ceniit.demo.user.infraestructure.input;

import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.*;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.SortOrder;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.Criteria;
import ar.edu.ceniit.demo.user.aplication.ports.input.UserUseCases;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.GetUserByUUIDResponse;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.UpdateUserDTO;
import ar.edu.ceniit.demo.user.infraestructure.input.dto.GetAllUsersRequest;
import ar.edu.ceniit.demo.user.infraestructure.input.dto.UserResponseDTO;
import ar.edu.ceniit.demo.user.infraestructure.input.mapper.UserDTOMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserUseCases userUseCases;
    private final UserDTOMapper userDTOMapper;

    public UserController(UserUseCases userUseCases, UserDTOMapper userDTOMapper) {
        this.userUseCases = userUseCases;
        this.userDTOMapper = userDTOMapper;
    }


    @GetMapping("/{uuid}")
    @PreAuthorize("#uuid.toString() == principal.getClaimAsString('sub') or hasRole('backend-admin')")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable String uuid) throws UserNotFoundException, UserBadRequestException {
        GetUserByUUIDResponse userResponse = userUseCases.getByUUID(UUID.fromString(uuid));
        return ResponseEntity.ok(userDTOMapper.toResponse(userResponse));
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("#uuid.toString() == principal.getClaimAsString('sub') or hasRole('backend-admin')")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID uuid, @Valid @RequestBody ar.edu.ceniit.demo.user.infraestructure.input.dto.UpdateUserDTO request) throws UserNotFoundException, BadRequestOnUpdateUserException, DuplicateEmailException, UserBadRequestException {
        UpdateUserDTO appUpdateDTO = new UpdateUserDTO(
                uuid,
                Optional.empty(),
                Optional.ofNullable(request.getFirstName()),
                Optional.ofNullable(request.getSecondName()),
                Optional.ofNullable(request.getSurname()),
                Optional.ofNullable(request.getSecondSurname())
        );

        userUseCases.updateUser(appUpdateDTO);

        GetUserByUUIDResponse userResponse = userUseCases.getByUUID(uuid);
        return ResponseEntity.ok(userDTOMapper.toResponse(userResponse));
    }
    @PreAuthorize("hasRole('delete-users')")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID uuid) throws UserNotFoundException, UserBadRequestException {
        userUseCases.deleteUser(uuid);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('backend-admin')")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "10") Integer limit,
            @RequestParam(required = false, defaultValue = "USERNAME") User.Field sortBy,
            @RequestParam(required = false, defaultValue = "ASC") SortOrder sortOrder,
            @RequestBody(required = false) GetAllUsersRequest filterRequest
    ) {
        Criteria criteria = userDTOMapper.toDomain(filterRequest);

        List<UserResponseDTO> users = userUseCases.getAllUsers(criteria, sortOrder, limit, offset)
                .stream()
                .map(userDTOMapper::toResponse)
                .toList();

        return ResponseEntity.ok(users);
    }
}
