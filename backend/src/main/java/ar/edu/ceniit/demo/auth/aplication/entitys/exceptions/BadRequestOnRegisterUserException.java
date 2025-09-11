package ar.edu.ceniit.demo.auth.aplication.entitys.exceptions;

import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.BadRequestOnCreateUserException;

public class BadRequestOnRegisterUserException extends RuntimeException {
        public enum Reason {
            INVALID_EMAIL("Invalid email format"),
            MISSING_REQUIRED_FIELDS("Missing required fields"),
            INVALID_USERNAME("Invalid username"),
            INVALID_SECOND_NAME("Invalid Second Name"),
            INVALID_FIRST_NAME("Invalid First Name"),
            INVALID_LAST_NAME("Invalid Last Name"),
            INVALID_SECOND_LAST_NAME("Invalid Second Last Name"), INVALID_PASSWORD("Invalid Password");
            private final String reason;
            Reason(String reason) {
                this.reason = reason;
            }
            public String getReasonMessage() {
                return reason;
            }
        }
        private Reason reason;
        public BadRequestOnRegisterUserException(Reason reason, Throwable cause) {
            super("Bad request: " + reason.name(), cause);
            this.reason = reason;
        }
        public BadRequestOnRegisterUserException(Reason reason) {
            super("Bad request: " + reason.name());
            this.reason = reason;
        }
        public Reason getReason() {
            return this.reason;
        }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
