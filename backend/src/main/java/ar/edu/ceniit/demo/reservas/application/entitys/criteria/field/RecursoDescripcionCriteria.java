package ar.edu.ceniit.demo.reservas.application.entitys.criteria.field;

import ar.edu.ceniit.demo.reservas.application.entitys.criteria.Criteria;
import ar.edu.ceniit.demo.reservas.application.entitys.criteria.CriteriaVisitor;
import ar.edu.ceniit.demo.reservas.application.entitys.criteria.StringOperator;

public class RecursoDescripcionCriteria implements Criteria {
    private final StringOperator operator;
    private final String descripcion;

    public RecursoDescripcionCriteria(StringOperator operator, String descripcion) {
        this.operator = operator;
        this.descripcion = descripcion;
    }

    public StringOperator getOperator() {
        return operator;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public <T> T accept(CriteriaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
