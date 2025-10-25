package ar.edu.ceniit.demo.reservas.application.entitys.criteria.field;

import ar.edu.ceniit.demo.reservas.application.entitys.criteria.Criteria;
import ar.edu.ceniit.demo.reservas.application.entitys.criteria.CriteriaVisitor;
import ar.edu.ceniit.demo.reservas.application.entitys.criteria.StringOperator;

public class RecursoNombreCriteria implements Criteria {
    private final StringOperator operator;
    private final String nombre;

    public RecursoNombreCriteria(StringOperator operator, String nombre) {
        this.operator = operator;
        this.nombre = nombre;
    }

    public StringOperator getOperator() {
        return operator;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public <T> T accept(CriteriaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
