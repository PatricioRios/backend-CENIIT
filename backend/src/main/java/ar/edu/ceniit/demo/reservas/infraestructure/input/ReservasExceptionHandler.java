package ar.edu.ceniit.demo.reservas.infraestructure.input;

import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.UserNotAuthenticatedException;
import ar.edu.ceniit.demo.reservas.application.entitys.exceptions.RecursoBadRequestException;
import ar.edu.ceniit.demo.reservas.application.entitys.exceptions.RecursoNotFoundException;
import ar.edu.ceniit.demo.reservas.application.entitys.exceptions.RecursoYaReservado;
import ar.edu.ceniit.demo.reservas.application.entitys.exceptions.ReservaNotFoundException;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserBadRequestException;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(assignableTypes = ReservasController.class)
public class ReservasExceptionHandler {

    private ResponseEntity<Map<String, String>> buildResponse(HttpStatus status, Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(RecursoBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleRecursoBadRequestException(RecursoBadRequestException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(RecursoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> handleRecursoNotFoundException(RecursoNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(ReservaNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> handleReservaNotFoundException(ReservaNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(RecursoYaReservado.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Map<String, String>> handleRecursoYaReservado(RecursoYaReservado ex) {
        return buildResponse(HttpStatus.CONFLICT, ex);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(UserBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleUserBadRequestException(UserBadRequestException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Map<String, String>> handleUserNotAuthenticatedException(UserNotAuthenticatedException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, ex);
    }
}
