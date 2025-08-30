package ar.edu.ceniit.demo.user.aplication.ports.input;

import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;

public interface GetUserByUUIDUseCase {
    User getByUUID(String uuid);
}
