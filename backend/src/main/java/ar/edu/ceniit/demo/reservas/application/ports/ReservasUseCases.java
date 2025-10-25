package ar.edu.ceniit.demo.reservas.application.ports;

import ar.edu.ceniit.demo.common.entitys.PagedResult;
import ar.edu.ceniit.demo.reservas.application.entitys.criteria.Criteria;
import ar.edu.ceniit.demo.reservas.application.entitys.objects.SortOrder;
import ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria.ReservaCriteria;
import ar.edu.ceniit.demo.reservas.domain.RecursoDisponibilidad;
import ar.edu.ceniit.demo.reservas.domain.Reserva;

import java.time.OffsetDateTime;

public interface ReservasUseCases {
    PagedResult<RecursoDisponibilidad> getAllRecursos(Criteria criteria, SortOrder sortOrder, int limit, int offset);
    Reserva createReserva(Reserva reserva, Integer solicitanteId);

    Reserva aprovarReserva(Integer reservaId, Integer approverId);
    Reserva cancelarReserva(Integer reservaId, Integer cancelerId);

    PagedResult<Reserva> getReservasForRecurso(Long recursoId, OffsetDateTime fechaDesde, OffsetDateTime fechaHasta, ReservaCriteria criteria, int limit, int offset);
}
