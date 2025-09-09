package ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.field;

import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.Criteria;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.CriteriaVisitor;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.StringOperator;

public class UserFirstNameCriteria implements Criteria {
    private final StringOperator operator;
    private final String firstName;
    public UserFirstNameCriteria(StringOperator operator, String firstName) {
        this.operator = operator;
        this.firstName = firstName;
    }

    public StringOperator getOperator() {
        return operator;
    }
    public String getFirstName() {
        return firstName;
    }

    @Override
    public <T> T accept(CriteriaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
