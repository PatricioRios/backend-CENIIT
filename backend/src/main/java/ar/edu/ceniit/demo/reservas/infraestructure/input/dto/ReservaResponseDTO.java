package ar.edu.ceniit.demo.reservas.infraestructure.input.dto;

import ar.edu.ceniit.demo.reservas.domain.EstadoReserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaResponseDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private OffsetDateTime fechaHoraInicio;
    private OffsetDateTime fechaHoraFin;
    private EstadoReserva estado;
    private Integer recursoSolicitadoId;
    private Integer usuarioSolicitanteId;
}
