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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController extends UserExceptionHandler {

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
        UpdateUserRequestDTO appUpdateDTO = new UpdateUserRequestDTO(
                UUID.fromString(request.getUuid()),
                Optional.ofNullable(request.getEmail()),
                Optional.ofNullable(request.getFirstName()),
                Optional.ofNullable(request.getSecondName()),
                Optional.ofNullable(request.getSurname()),
                Optional.ofNullable(request.getSecondSurname()),
                Optional.empty()
        );

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
        UpdateUserRequestDTO appUpdateDTO = new UpdateUserRequestDTO(
                UUID.fromString(request.getUuid()),
                Optional.ofNullable(request.getEmail()),
                Optional.ofNullable(request.getFirstName()),
                Optional.ofNullable(request.getSecondName()),
                Optional.ofNullable(request.getSurname()),
                Optional.ofNullable(request.getSecondSurname()),
                Optional.ofNullable(request.getDni())
        );

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
    @PostMapping()
    @Operation(
            summary = "Get all users with optional filtering, sorting, and pagination",
            description = "Returns a list of users. You can filter by various fields using a JSON object in the request body."
                    + "<h3>Available Fields for Filtering:</h3>"
                    + "<ul>"
                    + "<li><b>String Fields:</b> UUID, USERNAME, EMAIL, FIRST_NAME, LAST_NAME, DNI</li>"
                    + "<li><b>Date Fields (Use Numeric Operators for firtering this):</b> CREATED_AT, UPDATED_AT (use format YYYY-MM-DDTHH:MM:SSZ)</li>"
                    + "</ul>"
                    + "<h3>Available Operators:</h3>"
                    + "<ul>"
                    + "<li><b>For String fields:</b> EQUAL, NOT_EQUAL, LIKE, CONTAINS, START_WITH </li>"
                    + "<li><b>For Date and Numeric fields:</b> EQUAL, NOT_EQUAL, GREATER_THAN, LESS_THAN, GREATER_THAN_OR_EQUAL, LESS_THAN_OR_EQUAL</li>"
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
                                                        "type": "STRING_FIELD",
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
                                                            "type": "STRING_FIELD",
                                                            "field": "EMAIL",
                                                            "operator": "LIKE",
                                                            "value": "%@example.com"
                                                          },
                                                          {
                                                            "type": "STRING_FIELD",
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
    public ResponseEntity<List<UserResponseDTO>> searchUsers(
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "10") Integer limit,
            @RequestParam(required = false, defaultValue = "USERNAME") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") String sortOrder,
            @RequestBody(required = false) GetAllUsersRequest filterRequest
    ) throws HttpClientErrorException.BadRequest {

        Criteria criteria = userDTOMapper.toDomain(filterRequest);


        List<UserResponseDTO> users = userUseCases.getAllUsers(criteria, new SortOrder(stringToField(sortBy), stringToOrder(sortOrder)), limit, offset)
                .stream()
                .map(this.userDTOMapper::toResponse)
                .toList();

        return ResponseEntity.ok(users);
    }
    private SortOrder.Order stringToOrder(String order) throws HttpClientErrorException {
        try {
            switch (order.toUpperCase()) {
                case "ASC", "ASCENDENTE" -> order = "ASCENDENTE";
                case "DESC", "DESCENDENTE" -> order = "DESCENDENTE";
                case "UNSORTED", "UNSORT" -> order = "UNSORTED";
                default -> throw HttpClientErrorException.create(
                        "Invalid sort order",
                        HttpStatus.BAD_REQUEST,
                        null,
                        null,
                        null,
                        null
                );
            }
            return SortOrder.Order.valueOf(order.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid sort order: " + order);
        }
    }
    private User.Field stringToField(String fieldName) {
        try {
            return User.Field.valueOf(fieldName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw HttpClientErrorException.create(
                    "Invalid sort field",
                    HttpStatus.BAD_REQUEST,
                    null,
                    null,
                    null,
                    null
            );
        }
    }
}
