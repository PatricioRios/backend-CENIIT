package ar.edu.ceniit.demo.auth.aplication.entitys.exceptions;

public class BadRequest extends Exception {
    public BadRequest(String message) {
        super(message);
    }

    public BadRequest() {
        super();
    }

    public BadRequest(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequest(Throwable cause) {
        super(cause);
    }

    protected BadRequest(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
