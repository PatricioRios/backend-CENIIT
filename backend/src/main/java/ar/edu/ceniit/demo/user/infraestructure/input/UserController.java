package ar.edu.ceniit.demo.user.infraestructure.input;

import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserBaseException;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;
import ar.edu.ceniit.demo.user.aplication.ports.input.UserUseCases;
import ar.edu.ceniit.demo.user.infraestructure.input.dto.CreateUserDTO;
import ar.edu.ceniit.demo.user.infraestructure.input.dto.UpdateUserDTO;
import ar.edu.ceniit.demo.user.infraestructure.input.dto.UserResponseDTO;
import ar.edu.ceniit.demo.user.infraestructure.input.mapper.UserDTOMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserUseCases userUseCases;
    private final UserDTOMapper userDTOMapper;

    public UserController(UserUseCases userUseCases, UserDTOMapper userDTOMapper) {
        this.userUseCases = userUseCases;
        this.userDTOMapper = userDTOMapper;
    }

    @PostMapping()
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody CreateUserDTO request) throws UserBaseException {
        User user = userUseCases.createUser(userDTOMapper.toDomain(request));
        return new ResponseEntity<>(userDTOMapper.toResponse(user), HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable UUID uuid) throws UserBaseException {
        User user = userUseCases.getByUUID(uuid, uuid);
        return ResponseEntity.ok(userDTOMapper.toResponse(user));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID uuid, @Valid @RequestBody UpdateUserDTO request) throws UserBaseException {
        User user = userDTOMapper.toDomain(request);
        user.setUuid(uuid);
        User updatedUser = userUseCases.updateUser(user);
        return ResponseEntity.ok(userDTOMapper.toResponse(updatedUser));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID uuid) throws UserBaseException {
        userUseCases.deleteUser(uuid);
        return ResponseEntity.noContent().build();
    }
}
