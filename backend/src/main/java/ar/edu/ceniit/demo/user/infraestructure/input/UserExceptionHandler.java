package ar.edu.ceniit.demo.user.infraestructure.input;

import ar.edu.ceniit.demo.common.output.dto.ErrorResponseDTO;
import ar.edu.ceniit.demo.user.aplication.entitys.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice(
        basePackages = "ar.edu.ceniit.demo.user.infraestructure.input"
)
public class UserExceptionHandler {

    @ExceptionHandler(BadRequestOnCreateUserException.class)
    public ResponseEntity<ErrorResponseDTO<Void>> handleBadRequestOnCreateUserException(BadRequestOnCreateUserException e, HttpServletRequest request) {
        ErrorResponseDTO<Void> errorResponse = new ErrorResponseDTO<>(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                e.getReason().getReasonMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(BadRequestOnUpdateUserException.class)
    public ResponseEntity<ErrorResponseDTO<Void>> handleBadRequestOnUpdateUserException(BadRequestOnUpdateUserException e, HttpServletRequest request) {
        ErrorResponseDTO<Void> errorResponse = new ErrorResponseDTO<>(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                e.getReason().getReasonMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(Conflicts.class)
    public ResponseEntity<ErrorResponseDTO<Void>> handleConflicts(Conflicts e, HttpServletRequest request) {
        ErrorResponseDTO<Void> errorResponse = new ErrorResponseDTO<>(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponseDTO<Void>> handleDuplicateEmailException(DuplicateEmailException e, HttpServletRequest request) {
        ErrorResponseDTO<Void> errorResponse = new ErrorResponseDTO<>(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(IdCannotBeNull.class)
    public ResponseEntity<ErrorResponseDTO<Void>> handleIdCannotBeNull(IdCannotBeNull e, HttpServletRequest request) {
        ErrorResponseDTO<Void> errorResponse = new ErrorResponseDTO<>(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO<Void>> handleUserAlreadyExistsException(UserAlreadyExistsException e, HttpServletRequest request) {
        ErrorResponseDTO<Void> errorResponse = new ErrorResponseDTO<>(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(UserBadRequestException.class)
    public ResponseEntity<ErrorResponseDTO<Void>> handleUserBadRequestException(UserBadRequestException e, HttpServletRequest request) {
        ErrorResponseDTO<Void> errorResponse = new ErrorResponseDTO<>(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(UserNameIsAlreadyInUse.class)
    public ResponseEntity<ErrorResponseDTO<Void>> handleUserNameIsAlreadyInUse(UserNameIsAlreadyInUse e, HttpServletRequest request) {
        ErrorResponseDTO<Void> errorResponse = new ErrorResponseDTO<>(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO<Void>> handleUserNotFoundException(UserNotFoundException e, HttpServletRequest request) {
        ErrorResponseDTO<Void> errorResponse = new ErrorResponseDTO<>(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }
}