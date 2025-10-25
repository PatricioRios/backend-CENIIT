package ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria;

public interface ReservaCriteria {
    <T> T accept(ReservaCriteriaVisitor<T> visitor);
}
