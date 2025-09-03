package ar.edu.ceniit.demo.user.infraestructure.input.mapper;

import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;
import ar.edu.ceniit.demo.user.infraestructure.input.dto.CreateUserDTO;
import ar.edu.ceniit.demo.user.infraestructure.input.dto.UpdateUserDTO;
import ar.edu.ceniit.demo.user.infraestructure.input.dto.UserResponseDTO;

public class UserDTOMapper {

    public User toDomain(CreateUserDTO dto) {
        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .firstName(dto.getFirstName())
                .secondName(dto.getSecondName())
                .lastName(dto.getSurname())
                .secondLastName(dto.getSecondSurname())
                .dni(dto.getDni())
                .build();
    }

    public User toDomain(UpdateUserDTO dto) {
        return User.builder()
                .firstName(dto.getFirstName())
                .secondName(dto.getSecondName())
                .lastName(dto.getSurname())
                .secondLastName(dto.getSecondSurname())
                .dni(dto.getDni())
                .build();
    }

    public UserResponseDTO toResponse(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUuid(user.getUuid());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setSecondName(user.getSecondName());
        dto.setSurname(user.getLastName());
        dto.setSecondSurname(user.getSecondLastName());
        dto.setDni(user.getDni());
        return dto;
    }
}
