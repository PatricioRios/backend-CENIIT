package ar.edu.ceniit.demo.user.aplication.usecases;

import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.*;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.SortOrder;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.Criteria;
import ar.edu.ceniit.demo.user.aplication.ports.input.UserUseCases;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.GetUserByUUIDResponse;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.UpdateUserDTO;
import ar.edu.ceniit.demo.user.aplication.ports.output.OutputsUser;

import java.util.Set;
import java.util.UUID;

public class UserUseCasesImpl implements UserUseCases {
    private final OutputsUser userOutputs;
    public UserUseCasesImpl(OutputsUser userOutputs) {
        this.userOutputs = userOutputs;
    }
    @Override
    public User createUser(User user) throws
    BadRequestOnCreateUserException,
    UserNameIsAlreadyInUse,
    DuplicateEmailException,
    UserAlreadyExistsException {
        if(user.getUsername() == null || user.getUsername().isEmpty()){
            throw new BadRequestOnCreateUserException(BadRequestOnCreateUserException.Reason.INVALID_USERNAME);
        }
        try{
            user.verifyUser();
        } catch (User.EmailBadFormat e) {
            throw new BadRequestOnCreateUserException(BadRequestOnCreateUserException.Reason.INVALID_EMAIL);
        } catch (User.UserNameBadFormat e) {
            throw new BadRequestOnCreateUserException(BadRequestOnCreateUserException.Reason.INVALID_USERNAME);
        } catch (User.FirstNameBadFormat e) {
            throw new BadRequestOnCreateUserException(BadRequestOnCreateUserException.Reason.INVALID_FIRST_NAME);
        } catch (User.LastNameBadFormat e) {
            throw new BadRequestOnCreateUserException(BadRequestOnCreateUserException.Reason.INVALID_LAST_NAME);
        } catch (User.SecondLastNameBadFormat e) {
            throw new BadRequestOnCreateUserException(BadRequestOnCreateUserException.Reason.INVALID_SECOND_LAST_NAME);
        } catch (User.SecondNameBadFormat e) {
            throw new BadRequestOnCreateUserException(BadRequestOnCreateUserException.Reason.INVALID_SECOND_NAME);
        }
        return userOutputs.createUser(user);
    }

    @Override
    public void deleteUser(UUID uuid) throws
            UserBadRequestException,
            UserNotFoundException {
        if (uuid == null) {
            throw new UserBadRequestException();
        }
        userOutputs.deleteUserByUUID(uuid);
    }
    @Override
    public UpdateUserDTO updateUser(UpdateUserDTO user) throws
            BadRequestOnUpdateUserException,
            UserNotFoundException,
            DuplicateEmailException {
        user.validateUser();
        return userOutputs.updateUser(user);
    }

    @Override
    public GetUserByUUIDResponse getByUUID(UUID requestedUserId) throws
            UserNotFoundException,
            UserBadRequestException {

        if (requestedUserId == null) {
            throw new UserBadRequestException();
        }
        GetUserByUUIDResponse user = userOutputs.getUserByUUID(requestedUserId);
        if(user == null){
            throw new UserNotFoundException();
        }
        return user;
    }
    @Override
    public Set<User> getAllUsers(Criteria criteria, SortOrder sortOrder, int limit, int offset) {//TODO: Add more validations, And return correct and specific exceptions
        return this.userOutputs.getAllUsers(criteria, sortOrder, limit, offset);
    }
}