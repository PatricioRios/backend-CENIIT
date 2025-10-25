package ar.edu.ceniit.demo.reservas.infraestructure.input.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservaRequestDTO {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    private String nombre;
    
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;
    
    @NotNull(message = "La fecha de inicio es obligatoria")
    private OffsetDateTime fechaHoraInicio;
    
    @NotNull(message = "La fecha de fin es obligatoria")
    private OffsetDateTime fechaHoraFin;
    
    @AssertTrue(message = "La fecha de inicio debe ser anterior a la fecha de fin")
    public boolean isFechaInicioAntesDeFin() {
        if (fechaHoraInicio == null || fechaHoraFin == null) {
            return true;
        }
        return fechaHoraInicio.isBefore(fechaHoraFin);
    }
}
