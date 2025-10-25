package ar.edu.ceniit.demo.reservas.infraestructure.output.visitor;

import ar.edu.ceniit.demo.reservas.application.entitys.criteria.ComparableOperator;
import ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria.AndReservaCriteria;
import ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria.OrReservaCriteria;
import ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria.ReservaCriteriaVisitor;
import ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria.field.ReservaAprobadorOCanceladorCriteria;
import ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria.field.ReservaUsuarioSolicitanteCriteria;
import ar.edu.ceniit.demo.reservas.infraestructure.output.schema.ReservaEntityTable;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.stream.Collectors;

public class ReservaSpecificationVisitor implements ReservaCriteriaVisitor<Specification<ReservaEntityTable>> {

    @Override
    public Specification<ReservaEntityTable> visit(AndReservaCriteria criteria) {
        return Specification.allOf(criteria.getCriteria().stream()
                .map(c -> c.accept(this))
                .collect(Collectors.toList()));
    }

    @Override
    public Specification<ReservaEntityTable> visit(OrReservaCriteria criteria) {
        return Specification.anyOf(criteria.getCriteria().stream()
                .map(c -> c.accept(this))
                .collect(Collectors.toList()));
    }

    @Override
    public Specification<ReservaEntityTable> visit(ReservaUsuarioSolicitanteCriteria criteria) {
        return (root, query, builder) -> 
            createComparablePredicate(builder, root.get("solicitanteId"), criteria.getOperator(), criteria.getUsuarioId());
    }

    @Override
    public Specification<ReservaEntityTable> visit(ReservaAprobadorOCanceladorCriteria criteria) {
        return (root, query, builder) -> 
            createComparablePredicate(builder, root.get("aprobadorOCanceladorId"), criteria.getOperator(), criteria.getUsuarioId());
    }

    private Predicate createComparablePredicate(jakarta.persistence.criteria.CriteriaBuilder builder,
                                                jakarta.persistence.criteria.Path<Integer> path,
                                                ComparableOperator operator,
                                                Integer value) {
        switch (operator) {
            case EQUAL:
                return builder.equal(path, value);
            case NOT_EQUAL:
                return builder.notEqual(path, value);
            case GREATER_THAN:
                return builder.greaterThan(path, value);
            case LESS_THAN:
                return builder.lessThan(path, value);
            case GREATER_THAN_OR_EQUAL:
                return builder.greaterThanOrEqualTo(path, value);
            case LESS_THAN_OR_EQUAL:
                return builder.lessThanOrEqualTo(path, value);
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }
}
