package ar.edu.ceniit.demo.common.exceptions;

public class FatalErrorException extends RuntimeException {

    public enum FatalErrorType {
        SYSTEM_INCONSISTENCY,
        DATABASE_CONNECTION_FAILURE,
        CONFIGURATION_ERROR,
        OUT_OF_MEMORY,
        DISK_FAILURE,
        UNRECOVERABLE_SYSTEM_ERROR,
        UNDEFINED
    }

    private FatalErrorType errorType = FatalErrorType.UNDEFINED;

    public FatalErrorType getErrorType() {
        return errorType;
    }

    public FatalErrorException(String message, FatalErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }
    public FatalErrorException(String message,Throwable reason, FatalErrorType errorType) {
        super(message,reason);
        this.errorType = errorType;
    }
    public FatalErrorException(String message) {
        super(message);
    }

    public FatalErrorException(String message, Throwable cause) {
        super(message, cause);
    }


    public FatalErrorException(Throwable cause) {
        super(cause);
    }

    protected FatalErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
