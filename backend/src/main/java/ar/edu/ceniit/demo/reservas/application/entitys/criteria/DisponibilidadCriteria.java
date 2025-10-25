package ar.edu.ceniit.demo.reservas.application.entitys.criteria;

import java.time.OffsetDateTime;

public class DisponibilidadCriteria implements Criteria {
    private final OffsetDateTime fechaHora;

    public DisponibilidadCriteria(OffsetDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public OffsetDateTime getFechaHora() {
        return fechaHora;
    }

    @Override
    public <T> T accept(CriteriaVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
