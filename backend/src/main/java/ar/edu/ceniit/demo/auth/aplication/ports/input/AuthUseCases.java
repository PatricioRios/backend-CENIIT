package ar.edu.ceniit.demo.auth.aplication.ports.input;

import ar.edu.ceniit.demo.auth.aplication.ports.output.GetAllRolesOutput;

import java.util.Set;
import java.util.UUID;

public interface AuthUseCases extends
        RegisterNewUserUseCase,
        PutRolesToUserUseCase,
        GetAllRolesOutput,
        DeleteUserUseCase,
        UpdateBasicInfoUserUseCase
{
}
