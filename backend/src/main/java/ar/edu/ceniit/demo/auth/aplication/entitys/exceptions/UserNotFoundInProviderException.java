package ar.edu.ceniit.demo.auth.aplication.entitys.exceptions;

public class UserNotFoundInProviderException extends Exception {
    public UserNotFoundInProviderException(String message) {
        super(message);
    }

    public UserNotFoundInProviderException(String message, Throwable cause) {
        super(message, cause);
    }
}
