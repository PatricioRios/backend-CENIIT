package ar.edu.ceniit.demo.user.aplication.entitys.exceptions;

public class IdCannotBeNull extends Exception {
    public IdCannotBeNull() {
        super("UUID no puede ser nulo");
    }

    public IdCannotBeNull(String message) {
        super(message);
    }

    public IdCannotBeNull(String message, Throwable cause) {
        super(message, cause);
    }

    public IdCannotBeNull(Throwable cause) {
        super(cause);
    }

    protected IdCannotBeNull(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
