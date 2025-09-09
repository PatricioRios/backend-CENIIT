package ar.edu.ceniit.demo.user.aplication.entitys.exceptions;

public class BadRequestOnUpdateUserException extends Exception {
    private Reason reason;
    public enum Reason {
        INVALID_EMAIL("Invalid email format"),
        WEAK_PASSWORD("Weak password"),
        MISSING_REQUIRED_FIELDS("Missing required fields"),
        INVALID_USERNAME("Invalid username"),
        INVALID_UUID("Invalid UUID format"),
        INVALID_FIRST_NAME("Invalid First Name"),
        INVALID_SECOND_NAME("Invalid Second Name"),
        INVALID_LAST_NAME("Invalid Last Name"),
        INVALID_SECOND_LAST_NAME("Invalid Second Last Name"),;
        private final String reason;
        Reason(String reason) {
            this.reason = reason;
        }
    }

    public Reason getReason() {
        return reason;
    }

    public BadRequestOnUpdateUserException(BadRequestOnUpdateUserException.Reason reason, Throwable cause) {
        super("Bad request: " + reason.name(), cause);
        this.reason = reason;
    }
    public BadRequestOnUpdateUserException(BadRequestOnUpdateUserException.Reason reason) {
        super("Bad request: " + reason.name());
        this.reason = reason;
    }
}
