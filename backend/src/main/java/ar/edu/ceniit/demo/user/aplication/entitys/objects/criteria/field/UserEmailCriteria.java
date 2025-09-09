package ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.field;

import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.Criteria;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.CriteriaVisitor;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.StringOperator;

public class UserEmailCriteria implements Criteria {
    private final StringOperator operator;
    private final String email;
    public UserEmailCriteria(StringOperator operator, String lastName) {
        this.operator = operator;
        this.email = lastName;
    }

    public StringOperator getOperator() {
        return operator;
    }
    public String getEmail() {
        return email;
    }

    @Override
    public <T> T accept(CriteriaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
