package ar.edu.ceniit.demo.user.aplication.entitys.exceptions;


public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("User not found");
    }

    public UserNotFoundException(String s, Throwable e) {
        super(s, e);
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

    protected UserNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
