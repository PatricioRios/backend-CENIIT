package ar.edu.ceniit.demo.user.infraestructure.input;

import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.*;

import ar.edu.ceniit.demo.user.aplication.entitys.objects.PagedResult;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.SortOrder;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.Criteria;
import ar.edu.ceniit.demo.user.aplication.ports.input.UserUseCases;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.GetUserByUUIDResponse;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.UpdateUserRequestDTO;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.UpdateUserResponseDTO;
import ar.edu.ceniit.demo.user.infraestructure.input.dto.GetAllUsersRequest;
import ar.edu.ceniit.demo.user.infraestructure.input.dto.UpdateUserRequest;
import ar.edu.ceniit.demo.user.infraestructure.input.dto.UserResponseDTO;
import ar.edu.ceniit.demo.user.infraestructure.input.mapper.UserDTOMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController extends UserExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserUseCases userUseCases;
    private final UserDTOMapper userDTOMapper;

    public UserController(UserUseCases userUseCases, UserDTOMapper userDTOMapper) {
        this.userUseCases = userUseCases;
        this.userDTOMapper = userDTOMapper;
    }

    // Get user by UUID
    @GetMapping("/{uuid}")
    @PreAuthorize("#uuid.toString() == principal.getClaimAsString('sub') or hasRole('backend-admin')")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable String uuid) throws UserNotFoundException, UserBadRequestException {
        GetUserByUUIDResponse userResponse = userUseCases.getByUUID(UUID.fromString(uuid));
        return ResponseEntity.ok(userDTOMapper.toResponse(userResponse));
    }

    // Update user
    @PutMapping("/{uuid}")
    @PreAuthorize("#uuid.toString() == principal.getClaimAsString('sub') or hasRole('backend-admin')")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID uuid, @Valid @RequestBody UpdateUserRequest request)
            throws UserNotFoundException,
            BadRequestOnUpdateUserException,
            DuplicateEmailException,
            UserBadRequestException {

        logger.info("Received update request for user {}", uuid);

        UpdateUserRequestDTO appUpdateDTO = userDTOMapper.toUpdateUserRequestDTO(uuid, request, SecurityContextHolder.getContext().getAuthentication());

        UpdateUserResponseDTO user = userUseCases.updateUser(appUpdateDTO);

        UserResponseDTO userResponse = userDTOMapper.toResponse(user);

        return ResponseEntity.ok(userResponse);
    }

    // Delete user
    @PreAuthorize("hasRole('delete-users')")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID uuid) throws UserNotFoundException, UserBadRequestException {
        userUseCases.deleteUser(uuid);
        return ResponseEntity.noContent().build();
    }


    @PreAuthorize("hasRole('backend-admin')")
    @PostMapping("/search")
    @Operation(
            summary = "Search users with optional filtering, sorting, and pagination",
            description = "Returns a list of users based on complex filter criteria."
    )
    public ResponseEntity<PagedModel<EntityModel<UserResponseDTO>>> searchUsers(
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "10") Integer limit,
            @RequestParam(required = false, defaultValue = "USERNAME") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") String sortOrder,
            @RequestBody(required = false) GetAllUsersRequest filterRequest
    ) throws HttpClientErrorException.BadRequest, UserBadRequestException {



        Criteria criteria = userDTOMapper.toDomain(filterRequest);

        PagedResult<User> userPage = userUseCases.getAllUsers(criteria, new SortOrder(stringToField(sortBy), stringToOrder(sortOrder)), limit, offset);


        List<EntityModel<UserResponseDTO>> userResponseDTOs = userPage.getContent().stream()
                .map(this.userDTOMapper::toResponse)
                .map(user -> {
                    try {
                        return EntityModel.of(user,
                                linkTo(methodOn(UserController.class).getUser(user.getUuid().toString())).withSelfRel());
                    } catch (UserNotFoundException | UserBadRequestException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();

        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(
                userPage.getSize(),
                userPage.getNumber(),
                userPage.getTotalElements(),
                userPage.getTotalPages()
        );

        PagedModel<EntityModel<UserResponseDTO>> response = PagedModel.of(userResponseDTOs, pageMetadata);

        response.add(linkTo(methodOn(UserController.class).searchUsers(offset, limit, sortBy, sortOrder, filterRequest)).withSelfRel());

        if (!userPage.isLast()) {
            response.add(linkTo(methodOn(UserController.class).searchUsers(offset + limit, limit, sortBy, sortOrder, filterRequest)).withRel("next"));
        }

        if (!userPage.isFirst()) {
            response.add(linkTo(methodOn(UserController.class).searchUsers(offset - limit, limit, sortBy, sortOrder, filterRequest)).withRel("prev"));
        }

        return ResponseEntity.ok(response);
    }

    private SortOrder.Order stringToOrder(String order) throws UserBadRequestException {
        try {
            return switch (order.toUpperCase()) {
                case "ASC", "ASCENDENTE" -> SortOrder.Order.ASCENDENTE;
                case "DESC", "DESCENDENTE" -> SortOrder.Order.DESCENDENTE;
                case "UNSORTED", "UNSORT" -> SortOrder.Order.UNSORTED;
                default -> throw new UserBadRequestException("Invalid sort order: " + order);
            };
        } catch (IllegalArgumentException e) {
            throw new UserBadRequestException("Invalid sort order: " + order);
        }
    }

    private User.Field stringToField(String fieldName) throws UserBadRequestException {
        try {
            return User.Field.valueOf(fieldName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UserBadRequestException("Invalid field name: " + fieldName);
        }
    }
}