package ar.edu.ceniit.demo.user.aplication.entitys.exceptions;

public class UserNameIsAlreadyInUse extends UserBaseException {
    public UserNameIsAlreadyInUse(String message) {
        super(message);
    }
}
