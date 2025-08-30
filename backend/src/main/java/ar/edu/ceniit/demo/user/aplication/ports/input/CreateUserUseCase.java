package ar.edu.ceniit.demo.user.aplication.ports.input;

import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserBaseException;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;

public interface  CreateUserUseCase  {
    User createUser(User user) throws UserBaseException;
}

