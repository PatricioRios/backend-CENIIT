package ar.edu.ceniit.demo.user.aplication.entitys.exceptions;


import ar.edu.ceniit.demo.common.exceptions.InternalErrorException;

public class UserBadRequestException extends Exception {
    public UserBadRequestException(String message) {
        super(message);
    }
    public UserBadRequestException() {
        super("Bad Request");
    }
}