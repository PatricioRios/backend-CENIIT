package ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.field;

import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.Criteria;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.CriteriaVisitor;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.StringOperator;

public class UserUUIDCriteria implements Criteria {
    private final StringOperator operator;
    private final String value;

    public UserUUIDCriteria(StringOperator operator, String value) {
        this.operator = operator;
        this.value = value;
    }

    public StringOperator getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }

    @Override
    public <T> T accept(CriteriaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
