package ar.edu.ceniit.demo.user.aplication.ports.output;

import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;

public interface GetUserByUUIDOutput {
    User getUserByUUID(String uuid);
}
