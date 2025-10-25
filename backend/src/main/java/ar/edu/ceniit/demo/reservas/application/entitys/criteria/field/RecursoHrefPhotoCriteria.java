package ar.edu.ceniit.demo.reservas.application.entitys.criteria.field;

import ar.edu.ceniit.demo.reservas.application.entitys.criteria.Criteria;
import ar.edu.ceniit.demo.reservas.application.entitys.criteria.CriteriaVisitor;
import ar.edu.ceniit.demo.reservas.application.entitys.criteria.StringOperator;

public class RecursoHrefPhotoCriteria implements Criteria {
    private final StringOperator operator;
    private final String hrefPhoto;

    public RecursoHrefPhotoCriteria(StringOperator operator, String hrefPhoto) {
        this.operator = operator;
        this.hrefPhoto = hrefPhoto;
    }

    public StringOperator getOperator() {
        return operator;
    }

    public String getHrefPhoto() {
        return hrefPhoto;
    }

    @Override
    public <T> T accept(CriteriaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
