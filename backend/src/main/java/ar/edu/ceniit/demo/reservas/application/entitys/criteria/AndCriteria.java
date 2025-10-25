package ar.edu.ceniit.demo.reservas.application.entitys.criteria;

import java.util.Arrays;
import java.util.List;

public class AndCriteria implements Criteria {

    private final List<Criteria> criteria;

    public AndCriteria(Criteria... criteria) {
        this.criteria = Arrays.asList(criteria);
    }

    public List<Criteria> getCriteria() {
        return criteria;
    }

    @Override
    public <T> T accept(CriteriaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
