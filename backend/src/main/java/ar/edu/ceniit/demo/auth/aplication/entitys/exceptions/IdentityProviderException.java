package ar.edu.ceniit.demo.auth.aplication.entitys.exceptions;

public class IdentityProviderException extends AuthException {
    public IdentityProviderException(String message) {
        super(message);
    }

    public IdentityProviderException(String message, Throwable cause) {
        super(message, cause);
    }
}
