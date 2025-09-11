package ar.edu.ceniit.demo.user.aplication.entitys.exceptions;

public class UserNameIsAlreadyInUse extends Exception {
    public UserNameIsAlreadyInUse(String message) {
        super(message);
    }

    public UserNameIsAlreadyInUse() {
        super();
    }

    public UserNameIsAlreadyInUse(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNameIsAlreadyInUse(Throwable cause) {
        super(cause);
    }

    protected UserNameIsAlreadyInUse(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
