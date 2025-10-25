package ar.edu.ceniit.demo.reservas.application.entitys.criteria.field;

import ar.edu.ceniit.demo.reservas.application.entitys.criteria.ComparableOperator;
import ar.edu.ceniit.demo.reservas.application.entitys.criteria.Criteria;
import ar.edu.ceniit.demo.reservas.application.entitys.criteria.CriteriaVisitor;

public class RecursoIdCriteria implements Criteria {
    private final ComparableOperator operator;
    private final Long id;

    public RecursoIdCriteria(ComparableOperator operator, Long id) {
        this.operator = operator;
        this.id = id;
    }

    public ComparableOperator getOperator() {
        return operator;
    }

    public Long getId() {
        return id;
    }

    @Override
    public <T> T accept(CriteriaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
