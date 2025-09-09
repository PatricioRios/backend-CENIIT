package ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.field;

import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.Criteria;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.CriteriaVisitor;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.StringOperator;

public class UserLastNameCriteria implements Criteria {
    private final StringOperator operator;
    private final String lastName;
    public UserLastNameCriteria(StringOperator operator, String lastName) {
        this.operator = operator;
        this.lastName = lastName;
    }

    public StringOperator getOperator() {
        return operator;
    }
    public String getLastName() {
        return lastName;
    }

    @Override
    public <T> T accept(CriteriaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
