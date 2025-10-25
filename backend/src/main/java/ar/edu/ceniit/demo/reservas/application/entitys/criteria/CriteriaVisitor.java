package ar.edu.ceniit.demo.reservas.application.entitys.criteria;

import ar.edu.ceniit.demo.reservas.application.entitys.criteria.field.*;

public interface CriteriaVisitor<T> {
    T visit(AndCriteria criteria);
    T visit(OrCriteria criteria);
    T visit(RecursoNombreCriteria criteria);
    T visit(RecursoDescripcionCriteria criteria);
    T visit(RecursoEstadoCriteria criteria);
    T visit(RecursoIdCriteria criteria);
    T visit(RecursoCreatedAtCriteria criteria);
    T visit(RecursoUpdatedAtCriteria criteria);
    T visit(RecursoHrefPhotoCriteria criteria);
    T visit(DisponibilidadCriteria criteria);
}
