package ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria.field;

import ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria.ReservaCriteria;
import ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria.ReservaCriteriaVisitor;
import java.time.OffsetDateTime;

public class ReservaFechaHastaCriteria implements ReservaCriteria {
    private final OffsetDateTime value;

    public ReservaFechaHastaCriteria(OffsetDateTime value) {
        this.value = value;
    }

    public OffsetDateTime getValue() {
        return value;
    }

    @Override
    public <T> T accept(ReservaCriteriaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}