package ar.edu.ceniit.demo.user.aplication.entitys.exceptions;

public class Conflicts extends Exception {
    public Conflicts(String message) {
        super(message);
    }

    public Conflicts() {
        super();
    }

    public Conflicts(String message, Throwable cause) {
        super(message, cause);
    }

    public Conflicts(Throwable cause) {
        super(cause);
    }

    protected Conflicts(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
