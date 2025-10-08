package ar.edu.ceniit.demo.user.infraestructure.input.mapper;

import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.*;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.field.*;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.GetUserByUUIDResponse;
import ar.edu.ceniit.demo.user.infraestructure.input.dto.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserDTOMapper {

    public User toDomain(CreateUserDTO dto) {
        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .secondName(Optional.ofNullable(dto.getSecondName()))
                .lastName(dto.getSurname())
                .secondLastName(Optional.ofNullable(dto.getSecondSurname()))
                .dni(dto.getDni())
                .build();
    }

    public UserResponseDTO toResponse(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUuid(user.getUuid());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setSecondName(user.getSecondName().orElse(null));
        dto.setSurname(user.getLastName());
        dto.setSecondSurname(user.getSecondLastName().orElse(null));
        dto.setDni(user.getDni());
        return dto;
    }

    public UserResponseDTO toResponse(GetUserByUUIDResponse userRecord) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUuid(userRecord.uuid());
        dto.setUsername(userRecord.username());
        dto.setEmail(userRecord.email());
        dto.setFirstName(userRecord.firstName());
        dto.setSecondName(userRecord.secondName());
        dto.setSurname(userRecord.lastName());
        dto.setSecondSurname(userRecord.secondLastName());
        dto.setDni(userRecord.dni());
        return dto;
    }

    public Criteria toDomain(GetAllUsersRequest request) {
        if (request == null || request.getFilter() == null) {
            return null; // No filter applied
        }
        return toDomain(request.getFilter());
    }

    private Criteria toDomain(FilterDTO dto) {
        switch (dto) {
            case null -> {
                return null;
            }
            case AndFilterDTO andFilterDTO -> {
                List<Criteria> criteria = andFilterDTO.getFilters().stream()
                        .map(this::toDomain)
                        .collect(Collectors.toList());
                return new AndCriteria(criteria.toArray(new Criteria[0]));
            }
            case OrFilterDTO orFilterDTO -> {
                List<Criteria> criteria = orFilterDTO.getFilters().stream()
                        .map(this::toDomain)
                        .collect(Collectors.toList());
                return new OrCriteria(criteria.toArray(new Criteria[0]));
            }
            case StringComparationFieldFilterDTO fieldDto -> {
                String value = fieldDto.getValue();
                String op = fieldDto.getOperator().toUpperCase();

                switch (fieldDto.getField()) {
                    case UUID:
                        return new UserUUIDCriteria(StringOperator.valueOf(op), value);
                    case USERNAME:
                        return new UserNameCriteria(StringOperator.valueOf(op), value);
                    case EMAIL:
                        return new UserEmailCriteria(StringOperator.valueOf(op), value);
                    case FIRST_NAME:
                        return new UserFirstNameCriteria(StringOperator.valueOf(op), value);
                    case LAST_NAME:
                        return new UserLastNameCriteria(StringOperator.valueOf(op), value);
                    case DNI:
                        return new UserDNICriteria(StringOperator.valueOf(op), value);
                    case CREATED_AT:
                        return new UserCreatedAtCriteria(ComparableOperator.valueOf(op), OffsetDateTime.parse(value));
                    case UPDATED_AT:
                        return new UserUpdatedAtCriteria(ComparableOperator.valueOf(op), OffsetDateTime.parse(value));
                    default:
                        throw new IllegalArgumentException("Unsupported field for filtering: " + fieldDto.getField());
                }
            }
            default -> {
            }
        }

        throw new IllegalArgumentException("Unknown FilterDTO type: " + dto.getClass().getName());
    }

    public ar.edu.ceniit.demo.user.aplication.ports.input.dtos.UpdateUserRequestDTO toUpdateUserRequestDTO(java.util.UUID uuid, UpdateUserRequest request, org.springframework.security.core.Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_backend-admin"));

        return new ar.edu.ceniit.demo.user.aplication.ports.input.dtos.UpdateUserRequestDTO(
                uuid,
                Optional.ofNullable(request.getEmail()),
                Optional.ofNullable(request.getFirstName()),
                Optional.ofNullable(request.getSecondName()),
                Optional.ofNullable(request.getSurname()),
                Optional.ofNullable(request.getSecondSurname()),
                isAdmin ? Optional.ofNullable(request.getDni()) : Optional.empty()
        );
    }

    public UserResponseDTO toResponse(ar.edu.ceniit.demo.user.aplication.ports.input.dtos.UpdateUserResponseDTO user) {
        return UserResponseDTO.builder()
                .uuid(user.getUuid())
                .email(user.getEmail().orElse(null))
                .firstName(user.getFirstName().orElse(null))
                .secondName(user.getSecondName().orElse(null))
                .surname(user.getLastName().orElse(null))
                .secondSurname(user.getSecondLastName().orElse(null))
                .dni(user.getDni().orElse(null))
                .username(user.getUsername().orElse(null))
                .build();
    }
}
