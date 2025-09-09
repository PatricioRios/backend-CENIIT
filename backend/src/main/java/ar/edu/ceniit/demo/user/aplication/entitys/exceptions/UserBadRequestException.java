package ar.edu.ceniit.demo.user.aplication.entitys.exceptions;


public class UserBadRequestException extends Exception {
    public UserBadRequestException(String message) {
        super(message);
    }
    public UserBadRequestException() {
        super("Bad Request");
    }
}