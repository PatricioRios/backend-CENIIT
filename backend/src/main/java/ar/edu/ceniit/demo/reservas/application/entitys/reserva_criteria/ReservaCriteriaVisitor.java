package ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria;

import ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria.field.*;

public interface ReservaCriteriaVisitor<T> {
    T visit(AndReservaCriteria criteria);
    T visit(OrReservaCriteria criteria);
    T visit(ReservaUsuarioSolicitanteCriteria criteria);
    T visit(ReservaAprobadorOCanceladorCriteria criteria);
    T visit(ReservaRecursoIdCriteria criteria);
    T visit(ReservaFechaDesdeCriteria criteria);
    T visit(ReservaFechaHastaCriteria criteria);
}
