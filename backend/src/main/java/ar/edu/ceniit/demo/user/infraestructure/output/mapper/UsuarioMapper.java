package ar.edu.ceniit.demo.user.infraestructure.output.mapper;

import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.GetUserByUUIDResponse;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.UpdateUserRequestDTO;
import ar.edu.ceniit.demo.user.infraestructure.output.schema.UserEntityTable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class UsuarioMapper {

    public UserEntityTable toSchema(User user) {
        UserEntityTable schema = new UserEntityTable();
        schema.setUuid(user.getUuid());
        schema.setUsername(user.getUsername());
        schema.setEmail(user.getEmail());
        schema.setNombre(user.getFirstName());
        schema.setSegundoNombre(user.getSecondName().orElse(""));//problem me daba aca
        schema.setApellido(user.getLastName());
        schema.setSegundoApellido(user.getSecondLastName().orElse(""));
        schema.setDni(user.getDni());
        return schema;
    }

    public User toDomain(UserEntityTable schema) {
        return User.builder()
                .id(schema.getId())
                .uuid(schema.getUuid())
                .username(schema.getUsername())
                .email(schema.getEmail())
                .firstName(schema.getNombre())
                .secondName(Optional.ofNullable(schema.getSegundoNombre()))
                .lastName(schema.getApellido())
                .secondLastName(Optional.ofNullable(schema.getSegundoApellido()))
                .dni(schema.getDni())
                .createdAt(schema.getCreatedAt())
                .updatedAt(schema.getUpdatedAt())
                .build();
    }


    public GetUserByUUIDResponse toGetUserByUUIDResponse(UserEntityTable schema) {
        return new GetUserByUUIDResponse(
                schema.getUuid(),
                schema.getId(),
                schema.getUsername(),
                schema.getEmail(),
                schema.getNombre(),
                schema.getSegundoNombre(),
                schema.getApellido(),
                schema.getSegundoApellido(),
                schema.getDni(),
                schema.getCreatedAt(),
                schema.getUpdatedAt(),
                Set.of("user") // This is hardcoded in the mock. I'll do the same.
        );
    }

    public void updateSchemaFromDto(UpdateUserRequestDTO dto, UserEntityTable schema) {
        dto.getEmail().ifPresent(schema::setEmail);
        dto.getFirstName().ifPresent(schema::setNombre);
        dto.getLastName().ifPresent(schema::setApellido);
        dto.getSecondName().ifPresent(schema::setSegundoNombre);
        dto.getSecondLastName().ifPresent(schema::setSegundoApellido);
        dto.getDni().ifPresent(schema::setDni);
    }
}
