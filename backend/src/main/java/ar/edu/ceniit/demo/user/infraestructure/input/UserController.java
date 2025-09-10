package ar.edu.ceniit.demo.user.infraestructure.input;

import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.*;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.SortOrder;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.Criteria;
import ar.edu.ceniit.demo.user.aplication.ports.input.UserUseCases;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.GetUserByUUIDResponse;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.UpdateUserRequestDTO;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.UpdateUserResponseDTO;
import ar.edu.ceniit.demo.user.infraestructure.input.dto.GetAllUsersRequest;
import ar.edu.ceniit.demo.user.infraestructure.input.dto.UpdateByAdminDTO;
import ar.edu.ceniit.demo.user.infraestructure.input.dto.UpdateUserByUserDTO;
import ar.edu.ceniit.demo.user.infraestructure.input.dto.UserResponseDTO;
import ar.edu.ceniit.demo.user.infraestructure.input.mapper.UserDTOMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @PutMapping()
    @PreAuthorize("#request.uuid == principal.getClaimAsString('sub') or hasRole('backend-admin')")
    public ResponseEntity<UserResponseDTO> updateByUser(@Valid @RequestBody UpdateUserByUserDTO request) throws UserNotFoundException, BadRequestOnUpdateUserException, DuplicateEmailException, UserBadRequestException {
        System.out.println("Received update request: " + request);
        UpdateUserRequestDTO appUpdateDTO = new UpdateUserRequestDTO(
                UUID.fromString(request.getUuid()),
                Optional.ofNullable(request.getEmail()),
                Optional.ofNullable(request.getFirstName()),
                Optional.ofNullable(request.getSecondName()),
                Optional.ofNullable(request.getSurname()),
                Optional.ofNullable(request.getSecondSurname()),
                Optional.empty()
        );

        System.out.println("Received update request: " + request);

        UpdateUserResponseDTO user = userUseCases.updateUser(appUpdateDTO);


        UserResponseDTO userResponse = UserResponseDTO.builder()
                .uuid(user.getUuid())
                .email(user.getEmail().orElse(""))
                .firstName(user.getFirstName().orElse(""))
                .secondName(user.getSecondName().orElse(""))
                .surname(user.getLastName().orElse(""))
                .secondSurname(user.getSecondLastName().orElse(""))
                .dni(user.getDni().orElse(-1))
                .username(user.getUsername().orElse(""))
                .build();

        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/admin")
    @PreAuthorize("#request.uuid == principal.getClaimAsString('sub') or hasRole('backend-admin')")
    public ResponseEntity<UserResponseDTO> updateByAdmin(@Valid @RequestBody UpdateByAdminDTO request) throws UserNotFoundException, BadRequestOnUpdateUserException, DuplicateEmailException, UserBadRequestException {
        System.out.println("Received update request: " + request);
        UpdateUserRequestDTO appUpdateDTO = new UpdateUserRequestDTO(
                UUID.fromString(request.getUuid()),
                Optional.ofNullable(request.getEmail()),
                Optional.ofNullable(request.getFirstName()),
                Optional.ofNullable(request.getSecondName()),
                Optional.ofNullable(request.getSurname()),
                Optional.ofNullable(request.getSecondSurname()),
                Optional.ofNullable(request.getDni())
        );

        System.out.println("Received update request: " + request);

        UpdateUserResponseDTO user = userUseCases.updateUser(appUpdateDTO);


        UserResponseDTO userResponse = UserResponseDTO.builder()
                .uuid(user.getUuid())
                .email(user.getEmail().orElse(""))
                .firstName(user.getFirstName().orElse(""))
                .secondName(user.getSecondName().orElse(""))
                .surname(user.getLastName().orElse(""))
                .secondSurname(user.getSecondLastName().orElse(""))
                .dni(user.getDni().orElse(-1))
                .username(user.getUsername().orElse(""))
                .build();

        return ResponseEntity.ok(userResponse);
    }
    @PreAuthorize("hasRole('delete-users')")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID uuid) throws UserNotFoundException, UserBadRequestException {
        userUseCases.deleteUser(uuid);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('backend-admin')")
    @GetMapping()
    @Operation(
            summary = "Get all users with optional filtering, sorting, and pagination",
            description = "Returns a list of users. You can filter by various fields using a JSON object in the request body."
                    + "<h3>Available Fields for Filtering:</h3>"
                    + "<ul>"
                    + "<li><b>String Fields:</b> UUID, USERNAME, EMAIL, FIRST_NAME, LAST_NAME</li>"
                    + "<li><b>Integer Fields:</b> DNI</li>"
                    + "<li><b>Date Fields:</b> CREATED_AT, UPDATED_AT (use format YYYY-MM-DDTHH:MM:SSZ)</li>"
                    + "</ul>"
                    + "<h3>Available Operators:</h3>"
                    + "<ul>"
                    + "<li><b>For String and Integer fields:</b> EQUAL, NOT_EQUAL, LIKE, STARTS_WITH, CONTAINS</li>"
                    + "<li><b>For Date fields:</b> EQUAL, NOT_EQUAL, GREATER_THAN, LESS_THAN, GREATER_THAN_OR_EQUAL, LESS_THAN_OR_EQUAL</li>"
                    + "</ul>",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "JSON object for filtering users. You can create complex queries by nesting AND/OR filters.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetAllUsersRequest.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Simple Filter",
                                            summary = "Filter by username containing 'test'",
                                            value = """
                                                    {
                                                      "filter": {
                                                        "type": "FIELD",
                                                        "field": "USERNAME",
                                                        "operator": "CONTAINS",
                                                        "value": "test"
                                                      }
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "Complex Filter",
                                            summary = "Filter for users with email ending in '@example.com' AND first name 'John'",
                                            value = """
                                                    {
                                                      "filter": {
                                                        "type": "AND",
                                                        "filters": [
                                                          {
                                                            "type": "FIELD",
                                                            "field": "EMAIL",
                                                            "operator": "LIKE",
                                                            "value": "%@example.com"
                                                          },
                                                          {
                                                            "type": "FIELD",
                                                            "field": "FIRST_NAME",
                                                            "operator": "EQUAL",
                                                            "value": "John"
                                                          }
                                                        ]
                                                      }
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    )
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "10") Integer limit,
            @RequestParam(required = false, defaultValue = "USERNAME") User.Field sortBy,
            @RequestParam(required = false, defaultValue = "ASC") SortOrder sortOrder,
            @org.springframework.web.bind.annotation.RequestBody(required = false) GetAllUsersRequest filterRequest
    ) {
        Criteria criteria = userDTOMapper.toDomain(filterRequest);

        List<UserResponseDTO> users = userUseCases.getAllUsers(criteria, sortOrder, limit, offset)
                .stream()
                .map(userDTOMapper::toResponse)
                .toList();

        return ResponseEntity.ok(users);
    }
}
