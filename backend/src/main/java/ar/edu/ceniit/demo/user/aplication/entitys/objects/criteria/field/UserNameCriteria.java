package ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.field;

import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.Criteria;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.CriteriaVisitor;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.StringOperator;

public class UserNameCriteria implements Criteria {
    private final StringOperator operator;
    private final String name;
    public UserNameCriteria(StringOperator operator, String name) {
        this.operator = operator;
        this.name = name;
    }

    public StringOperator getOperator() {
        return operator;
    }
    public String getName() {
        return name;
    }

    @Override
    public <T> T accept(CriteriaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
