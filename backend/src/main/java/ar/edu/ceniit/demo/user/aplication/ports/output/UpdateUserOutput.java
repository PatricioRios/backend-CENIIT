package ar.edu.ceniit.demo.user.aplication.ports.output;

import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserNotFoundException;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;

public interface UpdateUserOutput {
    void updateUser(User user) throws UserNotFoundException;
}
