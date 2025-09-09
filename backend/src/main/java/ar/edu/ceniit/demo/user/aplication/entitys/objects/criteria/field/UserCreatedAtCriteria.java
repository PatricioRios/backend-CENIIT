package ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.field;

import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.ComparableOperator;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.Criteria;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.CriteriaVisitor;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.StringOperator;

import java.time.OffsetDateTime;
import java.util.Date;

public class UserCreatedAtCriteria implements Criteria {
    private final ComparableOperator operator;
    private final OffsetDateTime createdAt;

    public UserCreatedAtCriteria(ComparableOperator operator, OffsetDateTime createdAt) {
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
