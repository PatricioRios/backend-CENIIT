package ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria.field;

import ar.edu.ceniit.demo.reservas.application.entitys.criteria.ComparableOperator;
import ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria.ReservaCriteria;
import ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria.ReservaCriteriaVisitor;

public class ReservaRecursoIdCriteria implements ReservaCriteria {
    private final ComparableOperator operator;
    private final Long value;

    public ReservaRecursoIdCriteria(ComparableOperator operator, Long value) {
        this.operator = operator;
        this.value = value;
    }

    public ComparableOperator getOperator() {
        return operator;
    }

    public Long getValue() {
        return value;
    }

    @Override
    public <T> T accept(ReservaCriteriaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}