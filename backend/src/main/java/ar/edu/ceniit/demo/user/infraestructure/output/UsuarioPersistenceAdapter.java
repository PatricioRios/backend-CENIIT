package ar.edu.ceniit.demo.user.infraestructure.output;

import ar.edu.ceniit.demo.auth.aplication.ports.input.dto.UserForDomain;
import ar.edu.ceniit.demo.auth.aplication.ports.output.CreateUserOnDomainOutput;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.*;
import ar.edu.ceniit.demo.common.entitys.PagedResult;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.SortOrder;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.Criteria;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.GetUserByUUIDResponse;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.UpdateUserRequestDTO;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.UpdateUserResponseDTO;
import ar.edu.ceniit.demo.user.aplication.ports.output.UserOutputs;
import ar.edu.ceniit.demo.user.infraestructure.output.mapper.UsuarioMapper;
import ar.edu.ceniit.demo.user.infraestructure.output.repository.UsuarioRepository;
import ar.edu.ceniit.demo.user.infraestructure.output.schema.UserEntityTable;
import ar.edu.ceniit.demo.user.infraestructure.output.visitor.UserSpecificationVisitor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Primary
@RequiredArgsConstructor
public class UsuarioPersistenceAdapter implements UserOutputs, CreateUserOnDomainOutput {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public User createUser(User user) throws UserNameIsAlreadyInUse, DuplicateEmailException {
        if (usuarioRepository.existsByUsername(user.getUsername())) {
            throw new UserNameIsAlreadyInUse("Username " + user.getUsername() + " is already in use.");
        }
        if (usuarioRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateEmailException("Email " + user.getEmail() + " is already in use.");
        }
        UserEntityTable usuario = usuarioMapper.toSchema(user);
        if (usuario.getUuid() == null) {
            usuario.setUuid(UUID.randomUUID());
        }
        UserEntityTable savedUsuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDomain(savedUsuario);
    }

    @Override
    public void deleteUserByUUID(UUID uuid) throws UserNotFoundException {
        if (!usuarioRepository.existsByUuid(uuid)) {
            throw new UserNotFoundException();
        }
        usuarioRepository.deleteByUuid(uuid);
    }

    @Override
    public GetUserByUUIDResponse getUserByUUID(UUID uuid) throws UserNotFoundException {
        return usuarioRepository.findByUuid(uuid)
                .map(usuarioMapper::toGetUserByUUIDResponse)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UpdateUserResponseDTO updateUser(UpdateUserRequestDTO userDTO) throws UserNotFoundException, DuplicateEmailException {
        UserEntityTable usuario = usuarioRepository.findByUuid(userDTO.getUuid())
                .orElseThrow(UserNotFoundException::new);

        if (userDTO.getEmail().isPresent()) {
            String email = userDTO.getEmail().get();
            if (!email.equals(usuario.getEmail()) && usuarioRepository.existsByEmail(email)) {
                throw new DuplicateEmailException("Email " + email + " is already in use.");
            }
        }
        System.out.println("(persistance)Updating user: " + userDTO.getDni() );
        usuarioMapper.updateSchemaFromDto(userDTO, usuario);
        var userUpdated = usuarioMapper.toDomain(usuarioRepository.save(usuario));
        System.out.println("(persistance)User updated: " + userUpdated.getDni());
        return new UpdateUserResponseDTO(
                userUpdated.getUuid(),
                Optional.ofNullable(userUpdated.getEmail()),
                Optional.ofNullable(userUpdated.getFirstName()),
                userUpdated.getSecondName(),
                Optional.ofNullable(userUpdated.getLastName()),
                userUpdated.getSecondLastName(),
                Optional.ofNullable(userUpdated.getDni()),
                Optional.ofNullable(userUpdated.getUsername())
        );
    }

    @Override
    public PagedResult<User> getAllUsers(Criteria criteria, SortOrder sortOrder, int limit, int offset) {
        Specification<UserEntityTable> spec = (root, query, cb) -> cb.conjunction();
        if (criteria != null) {
            spec = criteria.accept(new UserSpecificationVisitor());
        }

        int page = offset / limit;

        Pageable pageable = PageRequest.of(page, limit);
        if (sortOrder != null && sortOrder.direction() != SortOrder.Order.UNSORTED) {
            Sort.Direction direction = sortOrder.direction() == SortOrder.Order.ASCENDENTE ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(page, limit, Sort.by(direction, sortOrder.fieldName().name().toLowerCase()));
        }

        Page<UserEntityTable> userPage = usuarioRepository.findAll(spec, pageable);
        List<User> users = userPage.getContent().stream()
                .map(usuarioMapper::toDomain)
                .collect(Collectors.toList());

        return new PagedResult<>(users, userPage.getTotalElements(), userPage.getNumber(), userPage.getSize(), userPage.getNumberOfElements(), userPage.isFirst(), userPage.isLast(), userPage.isEmpty());
    }

    @Override
    public void createUser(UserForDomain user) throws UserNameIsAlreadyInUse, DuplicateEmailException {
        User domainUser = User.builder()
                .uuid(user.getUuid())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .secondName(Optional.ofNullable(user.getSecondName()))
                .lastName(user.getLastName())
                .secondLastName(Optional.ofNullable(user.getSecondLastName()))
                .build();
        createUser(domainUser);
    }
}