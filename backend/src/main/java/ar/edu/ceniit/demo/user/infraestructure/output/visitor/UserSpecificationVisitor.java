package ar.edu.ceniit.demo.user.infraestructure.output.visitor;

import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.AndCriteria;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.CriteriaVisitor;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.OrCriteria;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.StringOperator;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.field.*;
import ar.edu.ceniit.demo.user.infraestructure.output.schema.UserEntityTable;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.stream.Collectors;

public class UserSpecificationVisitor implements CriteriaVisitor<Specification<UserEntityTable>> {

    @Override
    public Specification<UserEntityTable> visit(AndCriteria criteria) {
        return Specification.allOf(criteria.getCriteria().stream()
                .map(c -> c.accept(this))
                .collect(Collectors.toList()));
    }

    @Override
    public Specification<UserEntityTable> visit(OrCriteria criteria) {
        return Specification.anyOf(criteria.getCriteria().stream()
                .map(c -> c.accept(this))
                .collect(Collectors.toList()));
    }

    private Predicate createStringPredicate(jakarta.persistence.criteria.CriteriaBuilder builder,
                                            jakarta.persistence.criteria.Path<String> path,
                                            StringOperator operator,
                                            String value) {
        switch (operator) {
            case EQUAL:
                return builder.equal(path, value);
            case NOT_EQUAL:
                return builder.notEqual(path, value);
            case LIKE:
                return builder.like(path, value);
            case STARTS_WITH:
                return builder.like(path, value + "%");
            case CONTAINS:
                return builder.like(path, "%" + value + "%");
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }

    @Override
    public Specification<UserEntityTable> visit(UserUUIDCriteria criteria) {
        return (root, query, builder) -> createStringPredicate(builder, root.get("uuid"), criteria.getOperator(), criteria.getValue());
    }

    @Override
    public Specification<UserEntityTable> visit(UserNameCriteria criteria) {
        return (root, query, builder) -> createStringPredicate(builder, root.get("username"), criteria.getOperator(), criteria.getName());
    }

    @Override
    public Specification<UserEntityTable> visit(UserLastNameCriteria criteria) {
        return (root, query, builder) -> createStringPredicate(builder, root.get("lastName"), criteria.getOperator(), criteria.getLastName());
    }

    @Override
    public Specification<UserEntityTable> visit(UserFirstNameCriteria criteria) {
        return (root, query, builder) -> createStringPredicate(builder, root.get("firstName"), criteria.getOperator(), criteria.getFirstName());
    }

    @Override
    public Specification<UserEntityTable> visit(UserEmailCriteria criteria) {
        return (root, query, builder) -> createStringPredicate(builder, root.get("email"), criteria.getOperator(), criteria.getEmail());
    }

    @Override
    public Specification<UserEntityTable> visit(UserDNICriteria criteria) {
        return (root, query, builder) -> createStringPredicate(builder, root.get("dni"), criteria.getOperator(), criteria.getDni());
    }

    @Override
    public Specification<UserEntityTable> visit(UserCreatedAtCriteria criteria) {
        return (root, query, builder) -> {
            switch (criteria.getOperator()) {
                case EQUAL:
                    return builder.equal(root.get("createdAt"), criteria.getCreatedAt());
                case NOT_EQUAL:
                    return builder.notEqual(root.get("createdAt"), criteria.getCreatedAt());
                case GREATER_THAN:
                    return builder.greaterThan(root.get("createdAt"), criteria.getCreatedAt());
                case LESS_THAN:
                    return builder.lessThan(root.get("createdAt"), criteria.getCreatedAt());
                case GREATER_THAN_OR_EQUAL:
                    return builder.greaterThanOrEqualTo(root.get("createdAt"), criteria.getCreatedAt());
                case LESS_THAN_OR_EQUAL:
                    return builder.lessThanOrEqualTo(root.get("createdAt"), criteria.getCreatedAt());
                default:
                    throw new IllegalArgumentException("Unsupported operator for date: " + criteria.getOperator());
            }
        };
    }

    @Override
    public Specification<UserEntityTable> visit(UserUpdatedAtCriteria criteria) {
        return (root, query, builder) -> {
            switch (criteria.getOperator()) {
                case EQUAL:
                    return builder.equal(root.get("updatedAt"), criteria.getUpdatedAt());
                case NOT_EQUAL:
                    return builder.notEqual(root.get("updatedAt"), criteria.getUpdatedAt());
                case GREATER_THAN:
                    return builder.greaterThan(root.get("updatedAt"), criteria.getUpdatedAt());
                case LESS_THAN:
                    return builder.lessThan(root.get("updatedAt"), criteria.getUpdatedAt());
                case GREATER_THAN_OR_EQUAL:
                    return builder.greaterThanOrEqualTo(root.get("updatedAt"), criteria.getUpdatedAt());
                case LESS_THAN_OR_EQUAL:
                    return builder.lessThanOrEqualTo(root.get("updatedAt"), criteria.getUpdatedAt());
                default:
                    throw new IllegalArgumentException("Unsupported operator for date: " + criteria.getOperator());
            }
        };
    }
}