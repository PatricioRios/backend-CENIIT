package ar.edu.ceniit.demo.common.output.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

/**
 * DTO estándar para respuestas de error en la API.
 * El tipo genérico 'T' permite incluir detalles específicos y estructurados
 * para diferentes tipos de errores (ej. errores de validación).
 *
 * @param <T> El tipo de dato para el campo 'details'.
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // No incluye campos nulos en el JSON
public class ErrorResponseDTO<T> {

    /** La fecha y hora en que ocurrió el error. */
    private final LocalDateTime timestamp;

    /** El código de estado HTTP. */
    private final int status;

    /** La frase de razón del estado HTTP (ej. "Not Found"). */
    private final String error;

    /** Un mensaje descriptivo y legible del error. */
    private final String message;

    /** La ruta del endpoint que fue invocado. */
    private final String path;

    /** Datos adicionales y estructurados sobre el error. Puede ser nulo. */
    private T details;

    public ErrorResponseDTO(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
    public ErrorResponseDTO(int status, String error, String message, String path, T details) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.details = details;
    }

    // Getters
    public LocalDateTime getTimestamp() { return timestamp; }
    public int getStatus() { return status; }
    public String getError() { return error; }
    public String getMessage() { return message; }
    public String getPath() { return path; }
    public T getDetails() { return details; }

    // Setter para los detalles
    public void setDetails(T details) {
        this.details = details;
    }
}
