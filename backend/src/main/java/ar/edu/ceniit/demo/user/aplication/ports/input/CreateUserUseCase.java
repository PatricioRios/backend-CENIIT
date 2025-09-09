package ar.edu.ceniit.demo.user.aplication.ports.input;

import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.*;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;

//La interfaz se temrino de definir.
public interface  CreateUserUseCase  {
    User createUser(User user)
            throws
            BadRequestOnCreateUserException,
            UserNameIsAlreadyInUse,
            DuplicateEmailException,
            UserAlreadyExistsException;
}




