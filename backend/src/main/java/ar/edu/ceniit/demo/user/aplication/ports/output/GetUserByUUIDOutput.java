package ar.edu.ceniit.demo.user.aplication.ports.output;

import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserNotFoundException;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.GetUserByUUIDResponse;

import java.util.UUID;

public interface GetUserByUUIDOutput {
    GetUserByUUIDResponse getUserByUUID(UUID uuid) throws UserNotFoundException;
}
