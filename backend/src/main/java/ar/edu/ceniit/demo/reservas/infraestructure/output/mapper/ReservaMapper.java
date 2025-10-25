package ar.edu.ceniit.demo.reservas.infraestructure.output.mapper;

import ar.edu.ceniit.demo.reservas.domain.Reserva;
import ar.edu.ceniit.demo.reservas.infraestructure.output.schema.ReservaEntityTable;
import org.springframework.stereotype.Component;

@Component
public class ReservaMapper {

    public Reserva toDomain(ReservaEntityTable entity) {
        if (entity == null) {
            return null;
        }

        return new Reserva(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getFechaHoraInicio(),
                entity.getFechaHoraFin(),
                entity.getEstado(),
                entity.getRecursoSolicitadoId(),
                entity.getSolicitanteId(),
                entity.getAprobadorOCanceladorId()
        );
    }

    public ReservaEntityTable toEntity(Reserva domain) {
        if (domain == null) {
            return null;
        }

        ReservaEntityTable entity = new ReservaEntityTable();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        entity.setDescripcion(domain.getDescripcion());
        entity.setEstado(domain.getEstado());
        entity.setSolicitanteId(domain.getUsuarioSolicitanteId());
        entity.setAprobadorOCanceladorId(domain.getAprobadorOCanceladorId());
        entity.setRecursoSolicitadoId(domain.getRecursoSolicitadoId());
        entity.setFechaHoraInicio(domain.getFechaHoraInicio());
        entity.setFechaHoraFin(domain.getFechaHoraFin());

        return entity;
    }
}
