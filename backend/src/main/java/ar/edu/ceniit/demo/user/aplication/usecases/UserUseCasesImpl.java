package ar.edu.ceniit.demo.user.aplication.usecases;

import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.*;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;
import ar.edu.ceniit.demo.user.aplication.ports.input.UserUseCases;
import ar.edu.ceniit.demo.user.aplication.ports.output.OutputsUser;

import java.util.UUID;

public class UserUseCasesImpl implements UserUseCases {
    private final OutputsUser userOutputs;
    public UserUseCasesImpl(OutputsUser userOutputs) {
        this.userOutputs = userOutputs;
    }
    @Override
    public User createUser(User user) throws UserBaseException {
        if(user.getUsername() == null || user.getUsername().isEmpty()){
            throw new BadRequest("Username cannot be null or empty");
        }
        if(user.getPassword() == null || user.getPassword().isEmpty()){
            throw new BadRequest("Password cannot be null or empty");
        }
        if(!user.verifyPassword()){
            throw new BadRequest("Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, and one digit");
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
        userOutputs.updateUser(user);
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