package ar.edu.ceniit.demo.user.aplication.usecases;

import ar.edu.ceniit.demo.common.exceptions.FatalErrorException;
import ar.edu.ceniit.demo.common.exceptions.WarningErrorException;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.*;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.PagedResult;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.SortOrder;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.Criteria;
import ar.edu.ceniit.demo.user.aplication.ports.input.UserUseCases;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.GetUserByUUIDResponse;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.UpdateUserRequestDTO;
import ar.edu.ceniit.demo.user.aplication.ports.input.dtos.UpdateUserResponseDTO;
import ar.edu.ceniit.demo.user.aplication.ports.output.UserOutputs;
import ar.edu.ceniit.demo.user.aplication.ports.output.identity_provider.IPOutputs;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class UserUseCasesImpl implements UserUseCases {
    private final UserOutputs userOutputs;
    private final IPOutputs ipOutputs;
    public UserUseCasesImpl(UserOutputs userOutputs, IPOutputs ipOutputs) {
        this.ipOutputs = ipOutputs;
        this.userOutputs = userOutputs;
    }
    @Override
    public User createUser(User user) throws
    BadRequestOnCreateUserException,
    UserNameIsAlreadyInUse,
    DuplicateEmailException {
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

    public void deleteUser(UUID uuid) throws
            UserBadRequestException,
            UserNotFoundException {
        if (uuid == null) {
            throw new UserBadRequestException();
        }

        GetUserByUUIDResponse deletedUser = userOutputs.getUserByUUID(uuid);
        userOutputs.deleteUserByUUID(uuid);

        try {
            ipOutputs.deleteUser(uuid);
        }catch (UserNotFoundException e){
            throw new FatalErrorException(
                    "Critical error: user deleted from domain DB but not found in identity provider.",
                    e,
                    FatalErrorException.FatalErrorType.SYSTEM_INCONSISTENCY
                    );
        } catch (Exception ex) {
            try {
                Optional<String> secondName = deletedUser.secondName() != null ? Optional.of(deletedUser.secondName()) : Optional.empty();
                Optional<String> secondLastName = deletedUser.secondLastName() != null ? Optional.of(deletedUser.secondLastName()) : Optional.empty();
                userOutputs.createUser(new User(
                        deletedUser.uuid(),
                        deletedUser.id(),
                        deletedUser.username(),
                        deletedUser.email(),
                        deletedUser.firstName(),
                        secondName,
                        deletedUser.lastName(),
                        secondLastName,
                        deletedUser.dni(),
                        deletedUser.createdAt(),
                        deletedUser.updatedAt()
                ));
            } catch (Exception e) {
                // If rollback fails, log the error
                System.err.println("Rollback failed: " + ex.getMessage());
                throw new FatalErrorException(
                        "Critical error: user deleted from domain DB and rollback failed.",
                        e,
                        FatalErrorException.FatalErrorType.SYSTEM_INCONSISTENCY
                );
            }
            throw ex;
        }
    }
    @Override
    public UpdateUserResponseDTO updateUser(UpdateUserRequestDTO user) throws
            BadRequestOnUpdateUserException,
            UserNotFoundException,
            DuplicateEmailException {
        UpdateUserResponseDTO userReponse;
        user.validateUser();

        try{

            var beforeUpdating = userOutputs.getUserByUUID(user.getUuid());

            ipOutputs.updateUserOnIP(
                    user.getUuid(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getSecondName()
            );
            try {
                userReponse = userOutputs.updateUser(user);
            }catch (UserNotFoundException | DuplicateEmailException e){
                try {
                    ipOutputs.updateUserOnIP(
                            beforeUpdating.uuid(),
                            Optional.of(beforeUpdating.email()),
                            Optional.of(beforeUpdating.firstName()),
                            beforeUpdating.secondName() != null ? Optional.of(beforeUpdating.secondName()) : Optional.empty()
                    );
                } catch (Exception ex) {
                    throw new FatalErrorException(
                            "Critical error: user updated in identity provider but not found in domain DB.",
                            ex,
                            FatalErrorException.FatalErrorType.SYSTEM_INCONSISTENCY
                    );
                }
                throw new FatalErrorException(
                        "System inconsistency. The user is in the Identity Provider but not in the domain database. The user has a duplicate email address in the domain database but not in the Identity Provider (inconsistency).",
                        e,
                        FatalErrorException.FatalErrorType.SYSTEM_INCONSISTENCY
                );
            } catch (Exception e) {
                try {
                    ipOutputs.updateUserOnIP(
                            beforeUpdating.uuid(),
                            Optional.of(beforeUpdating.email()),
                            Optional.of(beforeUpdating.firstName()),
                            beforeUpdating.secondName() != null ? Optional.of(beforeUpdating.secondName()) : Optional.empty()
                    );
                } catch (Exception ex) {
                    throw new FatalErrorException(
                            "Critical error: user updated in identity provider but rollback failed.",
                            ex,
                            FatalErrorException.FatalErrorType.SYSTEM_INCONSISTENCY
                    );
                }
                throw new WarningErrorException("Error while updating user in domain DB", e);
            }
        }catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new WarningErrorException("Error while communicating with identity provider", e);
        }
        return userReponse;
    }

    @Override
    public GetUserByUUIDResponse getByUUID(UUID requestedUserId) throws
            UserNotFoundException,
            UserBadRequestException {

        if (requestedUserId == null) {
            throw new UserBadRequestException();
        }
        return userOutputs.getUserByUUID(requestedUserId);
    }

    @Override
    public PagedResult<User> getAllUsers(Criteria criteria, SortOrder sortOrder, int limit, int offset) {//TODO: Add more validations, And return correct and specific exceptions
        return this.userOutputs.getAllUsers(criteria, sortOrder, limit, offset);
    }
}