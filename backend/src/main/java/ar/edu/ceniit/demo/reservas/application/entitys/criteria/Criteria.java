package ar.edu.ceniit.demo.reservas.application.entitys.criteria;

public interface Criteria {
    <T> T accept(CriteriaVisitor<T> visitor);
}
