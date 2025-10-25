package ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria;

import ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria.field.ReservaAprobadorOCanceladorCriteria;
import ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria.field.ReservaUsuarioSolicitanteCriteria;

public interface ReservaCriteriaVisitor<T> {
    T visit(AndReservaCriteria criteria);
    T visit(OrReservaCriteria criteria);
    T visit(ReservaUsuarioSolicitanteCriteria criteria);
    T visit(ReservaAprobadorOCanceladorCriteria criteria);
}
