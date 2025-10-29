package ar.edu.ceniit.demo.reservas.application.ports;

import ar.edu.ceniit.demo.common.entitys.PagedResult;
import ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria.ReservaCriteria;
import ar.edu.ceniit.demo.reservas.domain.Reserva;

import java.time.OffsetDateTime;

public interface    ReservasRepositoryPort {
    boolean existeReserva(Integer recursoId, OffsetDateTime fechaHora);
    // Verifica si existe alguna reserva para el recurso dado en el rango de fechas y horas especificado
    // Si existe al menos una reserva que se solape con el rango, devuelve true; de lo contrario, false
    boolean existeReservaInRange(Integer recursoId, OffsetDateTime fechaHoraInicio, OffsetDateTime fechaHoraFin);
    Reserva createReserva(Reserva reserva, Integer solicitanteId);
    Reserva updateReserva(Reserva reserva);

    PagedResult<Reserva> searchReservas(ReservaCriteria criteria, int limit, int offset);
}
