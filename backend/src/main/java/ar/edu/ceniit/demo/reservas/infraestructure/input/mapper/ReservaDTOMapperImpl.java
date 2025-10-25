package ar.edu.ceniit.demo.reservas.infraestructure.input.mapper;

import ar.edu.ceniit.demo.reservas.domain.Reserva;
import ar.edu.ceniit.demo.reservas.infraestructure.input.dto.ReservaResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ReservaDTOMapperImpl implements ReservaDTOMapper {

    @Override
    public ReservaResponseDTO toDTO(Reserva reserva) {
        if (reserva == null) {
            return null;
        }
        
        return new ReservaResponseDTO(
                reserva.getId(),
                reserva.getNombre(),
                reserva.getDescripcion(),
                reserva.getFechaHoraInicio(),
                reserva.getFechaHoraFin(),
                reserva.getEstado(),
                reserva.getRecursoSolicitadoId(),
                reserva.getUsuarioSolicitanteId()
        );
    }

    @Override
    public Reserva toDomain(ReservaResponseDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return new Reserva(
                dto.getId(),
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getFechaHoraInicio(),
                dto.getFechaHoraFin(),
                dto.getEstado(),
                dto.getRecursoSolicitadoId(),
                dto.getUsuarioSolicitanteId(),
                null
        );
    }
}
