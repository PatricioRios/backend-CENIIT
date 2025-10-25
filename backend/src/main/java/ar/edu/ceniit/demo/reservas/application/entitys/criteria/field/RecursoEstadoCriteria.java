package ar.edu.ceniit.demo.reservas.application.entitys.criteria.field;

import ar.edu.ceniit.demo.reservas.application.entitys.criteria.ComparableOperator;
import ar.edu.ceniit.demo.reservas.application.entitys.criteria.Criteria;
import ar.edu.ceniit.demo.reservas.application.entitys.criteria.CriteriaVisitor;
import ar.edu.ceniit.demo.reservas.domain.EstadoRecurso;

public class RecursoEstadoCriteria implements Criteria {
    private final ComparableOperator operator;
    private final EstadoRecurso estado;

    public RecursoEstadoCriteria(ComparableOperator operator, EstadoRecurso estado) {
        this.operator = operator;
        this.estado = estado;
    }

    public ComparableOperator getOperator() {
        return operator;
    }

    public EstadoRecurso getEstado() {
        return estado;
    }

    @Override
    public <T> T accept(CriteriaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
