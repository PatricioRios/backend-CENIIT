package ar.edu.ceniit.demo.reservas.application.entitys.criteria.field;

import ar.edu.ceniit.demo.reservas.application.entitys.criteria.ComparableOperator;
import ar.edu.ceniit.demo.reservas.application.entitys.criteria.Criteria;
import ar.edu.ceniit.demo.reservas.application.entitys.criteria.CriteriaVisitor;

import java.time.OffsetDateTime;

public class RecursoUpdatedAtCriteria implements Criteria {
    private final ComparableOperator operator;
    private final OffsetDateTime updatedAt;

    public RecursoUpdatedAtCriteria(ComparableOperator operator, OffsetDateTime updatedAt) {
        this.operator = operator;
        this.updatedAt = updatedAt;
    }

    public ComparableOperator getOperator() {
        return operator;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public <T> T accept(CriteriaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
