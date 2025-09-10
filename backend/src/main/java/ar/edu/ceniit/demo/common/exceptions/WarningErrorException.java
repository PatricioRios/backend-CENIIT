package ar.edu.ceniit.demo.common.exceptions;

public class WarningErrorException extends RuntimeException {
    public WarningErrorException(String message) {
        super(message);
    }

    public WarningErrorException(String message, Throwable cause) {
        super(message, cause);
    }


    public WarningErrorException(Throwable cause) {
        super(cause);
    }

    protected WarningErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
