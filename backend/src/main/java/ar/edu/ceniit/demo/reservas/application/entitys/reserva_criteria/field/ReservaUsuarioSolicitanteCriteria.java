package ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria.field;

import ar.edu.ceniit.demo.reservas.application.entitys.criteria.ComparableOperator;
import ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria.ReservaCriteria;
import ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria.ReservaCriteriaVisitor;

public class ReservaUsuarioSolicitanteCriteria implements ReservaCriteria {
    private final ComparableOperator operator;
    private final Integer usuarioId;

    public ReservaUsuarioSolicitanteCriteria(ComparableOperator operator, Integer usuarioId) {
        this.operator = operator;
        this.usuarioId = usuarioId;
    }

    public ComparableOperator getOperator() {
        return operator;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    @Override
    public <T> T accept(ReservaCriteriaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}