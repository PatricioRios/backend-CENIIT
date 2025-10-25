package ar.edu.ceniit.demo.reservas.application.entitys.exceptions;

public class RecursoYaReservado extends RuntimeException {
    public RecursoYaReservado(String message) {
        super(message);
    }

    public RecursoYaReservado() {
        super();
    }

    public RecursoYaReservado(String message, Throwable cause) {
        super(message, cause);
    }

    public RecursoYaReservado(Throwable cause) {
        super(cause);
    }

    protected RecursoYaReservado(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
