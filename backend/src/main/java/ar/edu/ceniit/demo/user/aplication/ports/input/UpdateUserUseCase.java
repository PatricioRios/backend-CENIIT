package ar.edu.ceniit.demo.user.aplication.ports.input;

import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.BadRequestOnUpdateUserException;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.DuplicateEmailException;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserNotFoundException;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.UpdateUserDTO;

public interface UpdateUserUseCase {
    UpdateUserDTO updateUser(UpdateUserDTO user) throws
            BadRequestOnUpdateUserException,
            UserNotFoundException,
            DuplicateEmailException
            ;
}

