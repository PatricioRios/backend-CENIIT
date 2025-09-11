package ar.edu.ceniit.demo.user.aplication.entitys.exceptions;

public class DuplicateEmailException extends Exception {



    public DuplicateEmailException(String message) {
        super(message);
    }
    public DuplicateEmailException(String message, Throwable cause) {
        super(message,cause);
    }

    public DuplicateEmailException() {
        super("Email is already in use");
    }

    public DuplicateEmailException(Throwable cause) {
        super(cause);
    }

    protected DuplicateEmailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
