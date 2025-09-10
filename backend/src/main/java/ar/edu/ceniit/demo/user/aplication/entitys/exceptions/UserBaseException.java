package ar.edu.ceniit.demo.user.aplication.entitys.exceptions;

public class UserBaseException extends Exception{
    public UserBaseException() {
    }

    public UserBaseException(String message) {
        super(message);
    }

    public UserBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserBaseException(Throwable cause) {
        super(cause);
    }

    protected UserBaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
