package ar.edu.ceniit.demo.common.exceptions;

import ar.edu.ceniit.demo.common.output.dto.ErrorResponseDTO;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.Conflicts;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Conflicts.class)
    public ResponseEntity<Map<String, String>> handleConflictsException(Conflicts ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseDTO<Void>> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        log.warn("Authentication Failed: {} for request to {}", ex.getMessage(), request.getRequestURI());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponseDTO<>(
                        HttpStatus.UNAUTHORIZED.value(),
                        HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                        ex.getMessage(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO<Void>> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        log.warn("Access Denied: {} for request to {}", ex.getMessage(), request.getRequestURI());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponseDTO<>(
                        HttpStatus.FORBIDDEN.value(),
                        HttpStatus.FORBIDDEN.getReasonPhrase(),
                        ex.getMessage(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO<Void>> handleGlobalException(Exception ex, HttpServletRequest request) {
        log.error("e: ", ex);
        return ResponseEntity.internalServerError().body(
                new ErrorResponseDTO<>(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        "An unexpected error occurred" ,
                        request.getRequestURI()
                )
        );
    }
    @ExceptionHandler(WarningErrorException.class)
    public ResponseEntity<ErrorResponseDTO<Void>> handleGlobalException(WarningErrorException ex, HttpServletRequest request) {
        log.warn("e: {}", ex.toString(), ex);
        return ResponseEntity.internalServerError().body(
                new ErrorResponseDTO<>(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        "An unexpected error occurred" ,
                        request.getRequestURI()
                )
        );
    }
    @ExceptionHandler(FatalErrorException.class)
    public ResponseEntity<ErrorResponseDTO<Void>> handleGlobalException(FatalErrorException ex, HttpServletRequest request) {
        log.error("e: ", ex);
        return ResponseEntity.internalServerError().body(
                new ErrorResponseDTO<>(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        "An unexpected error occurred" ,
                        request.getRequestURI()
                )
        );
    }
    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<ErrorResponseDTO<Void>> handleGlobalException(InternalErrorException ex, HttpServletRequest request) {
        log.error("e: ", ex);
        return ResponseEntity.internalServerError().body(
                new ErrorResponseDTO<>(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        "An unexpected error occurred" ,
                        request.getRequestURI()
                )
        );
    }
}