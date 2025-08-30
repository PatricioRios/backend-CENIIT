package ar.edu.ceniit.demo.user.aplication.ports.output;

import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;

import java.util.UUID;

public interface GetUserByUUIDOutput {
    User getUserByUUID(UUID uuid);
}
