package ar.edu.ceniit.demo.reservas.application.entitys.exceptions;

public class ReservaNotFoundException extends RuntimeException {
    public ReservaNotFoundException(String message) {
        super(message);
    }
}
