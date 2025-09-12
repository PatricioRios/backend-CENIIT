package ar.edu.ceniit.demo.auth.infraestructure.ports.output;

import ar.edu.ceniit.demo.auth.aplication.entitys.exceptions.*;
import ar.edu.ceniit.demo.common.output.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice()
public class AuthExceptionHandler {
    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<ErrorResponseDTO<Void>> handleAllExceptions(BadRequest ex, HttpServletRequest request) {
        // Aquí puedes manejar la excepción, por ejemplo, registrarla o devolver una respuesta personalizada.
        ErrorResponseDTO<Void> errorResponse = new ErrorResponseDTO<>(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                request.getRequestURI()
        );
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }
    //TODO: Posibles mejoras
    @ExceptionHandler(Conflicts.class)
    public ResponseEntity<ErrorResponseDTO<Void>> handleAllExceptions(Conflicts ex, HttpServletRequest request) {
        // Aquí puedes manejar la excepción, por ejemplo, registrarla o devolver una respuesta personalizada.
        ErrorResponseDTO<Void> errorResponse = new ErrorResponseDTO<>(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                request.getRequestURI()
        );
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }
    @ExceptionHandler(BadRequestOnRegisterUserException.class)
    public ResponseEntity<ErrorResponseDTO<Void>> handleAllExceptions(BadRequestOnRegisterUserException ex, HttpServletRequest request) {
        // Aquí puedes manejar la excepción, por ejemplo, registrarla o devolver una respuesta personalizada.
        ErrorResponseDTO<Void> errorResponse = new ErrorResponseDTO<>(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getReason().getReasonMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }
    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseEntity<ErrorResponseDTO<Void>> handleAllExceptions(DuplicatedEmailException ex, HttpServletRequest request) {
        // Aquí puedes manejar la excepción, por ejemplo, registrarla o devolver una respuesta personalizada.
        ErrorResponseDTO<Void> errorResponse = new ErrorResponseDTO<>(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                "Email is already in use",
                request.getRequestURI()
        );
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }
    @ExceptionHandler(UserNameIsAlreadyInUse.class)
    public ResponseEntity<ErrorResponseDTO<Void>> handleAllExceptions(UserNameIsAlreadyInUse ex, HttpServletRequest request) {
        // Aquí puedes manejar la excepción, por ejemplo, registrarla o devolver una respuesta personalizada.
        ErrorResponseDTO<Void> errorResponse = new ErrorResponseDTO<>(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                "User name is already in use",
                request.getRequestURI()
        );
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }
    @ExceptionHandler(UserNotFoundInProviderException.class)
    public ResponseEntity<ErrorResponseDTO<Void>> handleAllExceptions(UserNotFoundInProviderException ex, HttpServletRequest request) {
        // Aquí puedes manejar la excepción, por ejemplo, registrarla o devolver una respuesta personalizada.
        ErrorResponseDTO<Void> errorResponse = new ErrorResponseDTO<>(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                "User not found in provider",
                request.getRequestURI()
        );
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }
}
