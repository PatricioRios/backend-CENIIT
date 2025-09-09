package ar.edu.ceniit.demo.user.aplication.ports.input;

import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserBaseException;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;

import java.util.UUID;

public interface UserUseCases extends
        CreateUserUseCase,
        UpdateUserUseCase,
        DeleteUserUseCase,
        GetUserByUUIDUseCase,
        GetAllUsersUseCase
{
}
