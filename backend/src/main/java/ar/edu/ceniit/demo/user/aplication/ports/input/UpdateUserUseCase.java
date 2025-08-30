package ar.edu.ceniit.demo.user.aplication.ports.input;

import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserBaseException;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserNotFoundException;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;

public interface UpdateUserUseCase {
    User updateUser(User user) throws UserBaseException;
}
