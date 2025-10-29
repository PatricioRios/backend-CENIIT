package ar.edu.ceniit.demo.auth.aplication.entitys.exceptions;

public class UserNotAuthenticatedException extends Exception {
    public UserNotAuthenticatedException(String message) {
        super(message);
    }
}
