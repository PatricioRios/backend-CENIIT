package ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria;

import java.util.Arrays;
import java.util.List;

public class OrReservaCriteria implements ReservaCriteria {

    private final List<ReservaCriteria> criteria;

    public OrReservaCriteria(ReservaCriteria... criteria) {
        this.criteria = Arrays.asList(criteria);
    }

    public List<ReservaCriteria> getCriteria() {
        return criteria;
    }

    @Override
    public <T> T accept(ReservaCriteriaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
