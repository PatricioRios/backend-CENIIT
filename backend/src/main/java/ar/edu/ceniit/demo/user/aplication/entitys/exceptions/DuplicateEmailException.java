package ar.edu.ceniit.demo.user.aplication.entitys.exceptions;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
