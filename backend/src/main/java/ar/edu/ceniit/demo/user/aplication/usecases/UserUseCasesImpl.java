package ar.edu.ceniit.demo.user.aplication.usecases;

import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.*;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;
import ar.edu.ceniit.demo.user.aplication.ports.input.UserUseCases;
import ar.edu.ceniit.demo.user.aplication.ports.output.CreateUserOutput;
import ar.edu.ceniit.demo.user.aplication.ports.output.UserOutputs;

import java.util.UUID;

public class UserUseCasesImpl implements UserUseCases {
    private final UserOutputs userOutputs;
    public UserUseCasesImpl(UserOutputs userOutputs) {
        this.userOutputs = userOutputs;
    }
    @Override
    public User createUser(User user) throws UserBaseException {
        if(!user.verifyPassword()){
            throw new BadRequest("Password and confirm password do not match");
        }
        return userOutputs.createUser(user);
    }

    @Override
    public void deleteUser(UUID uuid) throws IdCannotBeNull, UserNotFoundException {

        if (uuid == null) {
            throw new IdCannotBeNull();
        }
        userOutputs.deleteUserByUUID(uuid);
    }
    @Override
    public User updateUser(User user) throws UserBaseException {
        this.userOutputs.updateUser(user);
        return user;
    }

    @Override
    public User getByUUID(UUID authUserId, UUID requestedUserId) throws UserBaseException {
        if (requestedUserId == null) {
            throw new UserNotFoundException();
        }
        User user = userOutputs.getUserByUUID(authUserId);
        if(user == null){
            throw new UserNotFoundException();
        }
        // For security reasons, do not return the password
        user.setPassword("");
        return user;
    }
}