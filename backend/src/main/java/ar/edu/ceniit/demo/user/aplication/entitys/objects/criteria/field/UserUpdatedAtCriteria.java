package ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.field;

import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.ComparableOperator;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.Criteria;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.CriteriaVisitor;

import java.time.OffsetDateTime;

public class UserUpdatedAtCriteria implements Criteria {
    private final ComparableOperator operator;
    private final OffsetDateTime updatedAt;

    public UserUpdatedAtCriteria(ComparableOperator operator, OffsetDateTime updatedAt) {
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
