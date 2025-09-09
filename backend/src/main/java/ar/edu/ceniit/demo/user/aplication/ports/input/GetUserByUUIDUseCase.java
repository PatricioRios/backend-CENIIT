package ar.edu.ceniit.demo.user.aplication.ports.input;

import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserBadRequestException;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserBaseException;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserNotFoundException;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.GetUserByUUIDResponse;

import java.util.UUID;

public interface GetUserByUUIDUseCase {
    GetUserByUUIDResponse getByUUID(UUID requestedUserId) throws UserNotFoundException, UserBadRequestException;
}
