package ar.edu.ceniit.demo.reservas.application.entitys.criteria.field;

import ar.edu.ceniit.demo.reservas.application.entitys.criteria.ComparableOperator;
import ar.edu.ceniit.demo.reservas.application.entitys.criteria.Criteria;
import ar.edu.ceniit.demo.reservas.application.entitys.criteria.CriteriaVisitor;

import java.time.OffsetDateTime;

public class RecursoCreatedAtCriteria implements Criteria {
    private final ComparableOperator operator;
    private final OffsetDateTime createdAt;

    public RecursoCreatedAtCriteria(ComparableOperator operator, OffsetDateTime createdAt) {
        this.operator = operator;
        this.createdAt = createdAt;
    }

    public ComparableOperator getOperator() {
        return operator;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public <T> T accept(CriteriaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
