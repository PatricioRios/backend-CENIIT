package ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.field;

import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.Criteria;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.CriteriaVisitor;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.StringOperator;

public class UserDNICriteria implements Criteria {
    private final StringOperator operator;
    private final String dni;
    public UserDNICriteria(StringOperator operator, String dni) {
        this.operator = operator;
        this.dni = dni;
    }

    public StringOperator getOperator() {
        return operator;
    }
    public String getDni() {
        return dni;
    }

    @Override
    public <T> T accept(CriteriaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
