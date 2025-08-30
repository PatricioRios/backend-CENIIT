package ar.edu.ceniit.demo.user.aplication.ports.output;

import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;

public interface CreateUserOutput {
    User createUser(User user);
}
