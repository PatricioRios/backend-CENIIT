package ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria;

import java.util.Arrays;
import java.util.List;

public class AndCriteria implements Criteria {

    private final List<Criteria> criteria;

    /**
     * Construye un criterio AND a partir de una lista de otros criterios.
     * @param criteria Los criterios hijos que se unirán con el operador AND.
     */
    public AndCriteria(Criteria... criteria) {
        this.criteria = Arrays.asList(criteria);
    }

    /**
     * Devuelve la lista de criterios hijos.
     * El visitante utilizará este método para acceder y procesar cada criterio individualmente.
     * @return La lista de criterios hijos.
     */
    public List<Criteria> getCriteria() {
        return criteria;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T accept(CriteriaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
