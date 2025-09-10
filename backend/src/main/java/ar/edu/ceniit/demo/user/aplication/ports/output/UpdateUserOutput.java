package ar.edu.ceniit.demo.user.aplication.ports.output;

import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.BadRequestOnUpdateUserException;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.DuplicateEmailException;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserNotFoundException;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.UpdateUserRequestDTO;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.UpdateUserResponseDTO;

public interface UpdateUserOutput {
    UpdateUserResponseDTO updateUser(UpdateUserRequestDTO user) throws
            BadRequestOnUpdateUserException,
            UserNotFoundException,
            DuplicateEmailException;
}
