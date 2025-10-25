package ar.edu.ceniit.demo.reservas.application.ports;

import ar.edu.ceniit.demo.common.entitys.PagedResult;
import ar.edu.ceniit.demo.reservas.application.entitys.criteria.Criteria;
import ar.edu.ceniit.demo.reservas.application.entitys.objects.SortOrder;
import ar.edu.ceniit.demo.reservas.domain.Recurso;
import ar.edu.ceniit.demo.reservas.domain.Reserva;

public interface ReservasOutputPort {
    PagedResult<Recurso> findByCriteria(Criteria criteria, SortOrder sortOrder, int limit, int offset);

}
