package ar.edu.ceniit.demo.auth.aplication.entitys.exceptions;

public class DuplicatedEmailException extends RuntimeException {
    public DuplicatedEmailException(String message) {
        super(message);
    }
    public DuplicatedEmailException() {
        super("The email is already in use by another user.");
    }


    public DuplicatedEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedEmailException(Throwable cause) {
        super(cause);
    }

    protected DuplicatedEmailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
