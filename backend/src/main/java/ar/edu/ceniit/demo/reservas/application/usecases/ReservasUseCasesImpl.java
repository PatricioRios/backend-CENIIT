package ar.edu.ceniit.demo.reservas.application.usecases;

import ar.edu.ceniit.demo.common.entitys.PagedResult;
import ar.edu.ceniit.demo.reservas.application.entitys.criteria.*;
import ar.edu.ceniit.demo.reservas.application.entitys.exceptions.RecursoYaReservado;
import ar.edu.ceniit.demo.reservas.application.entitys.objects.SortOrder;
import ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria.ReservaCriteria;
import ar.edu.ceniit.demo.reservas.application.ports.ReservasOutputPort;
import ar.edu.ceniit.demo.reservas.application.ports.ReservasRepositoryPort;
import ar.edu.ceniit.demo.reservas.application.ports.ReservasUseCases;
import ar.edu.ceniit.demo.reservas.domain.*;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservasUseCasesImpl implements ReservasUseCases {

    private final ReservasOutputPort reservasOutputPort;
    private final ReservasRepositoryPort reservasRepositoryPort;

    public ReservasUseCasesImpl(ReservasOutputPort reservasOutputPort, ReservasRepositoryPort reservasRepositoryPort) {
        this.reservasOutputPort = reservasOutputPort;
        this.reservasRepositoryPort = reservasRepositoryPort;
    }

    @Override
    public PagedResult<RecursoDisponibilidad> getAllRecursos(Criteria criteria, SortOrder sortOrder, int limit, int offset) {
        CriteriaSeparationHelper separationHelper = new CriteriaSeparationHelper(criteria);
        Criteria resourceCriteria = separationHelper.getResourceCriteria();
        Optional<OffsetDateTime> availabilityDate = separationHelper.getAvailabilityDate();

        PagedResult<Recurso> pagedRecursos = reservasOutputPort.findByCriteria(resourceCriteria, sortOrder, limit, offset);

        List<RecursoDisponibilidad> disponibilidadList = pagedRecursos.getContent().stream()
                .map(recurso -> {
                    EstadoDisponibilidad estado = calculateDisponibilidad(recurso, availabilityDate);
                    return new RecursoDisponibilidad(recurso, estado);
                })
                .collect(Collectors.toList());

        return new PagedResult<>(
                disponibilidadList,
                pagedRecursos.getTotalElements(),
                pagedRecursos.getNumber(),
                pagedRecursos.getSize(),
                pagedRecursos.getNumberOfElements(),
                pagedRecursos.isFirst(),
                pagedRecursos.isLast(),
                pagedRecursos.isEmpty()
        );
    }

    @Override
    public Reserva createReserva(Reserva reserva,Integer solicitanteId) {
        boolean existeReserva = reservasRepositoryPort.existeReservaInRange(
                reserva.getRecursoSolicitadoId(),
                reserva.getFechaHoraInicio(),
                reserva.getFechaHoraFin()
        );
        if(existeReserva){
            throw new RecursoYaReservado("El recurso ya está reservado en el rango de fechas especificado.");
        }
        Reserva nuevaReserva = reservasRepositoryPort.createReserva(reserva,solicitanteId);
        return nuevaReserva;
    }

    @Override
    public Reserva aprovarReserva(Integer reservaId, Integer approverId) {
        Reserva reserva = new Reserva(
                reservaId,
                approverId,
                EstadoReserva.CONFIRMADA
        );
        Reserva nuevoEstado = reservasRepositoryPort.updateReserva(reserva);
        return nuevoEstado;
    }

    @Override
    public Reserva cancelarReserva(Integer reservaId, Integer cancelerId) {
        Reserva reserva = new Reserva(
                reservaId,
                cancelerId,
                EstadoReserva.CANCELADA
        );
        Reserva nuevoEstado = reservasRepositoryPort.updateReserva(reserva);
        return nuevoEstado;
    }

    @Override
    public PagedResult<Reserva> searchReservas(ReservaCriteria criteria, int limit, int offset) {
        return reservasRepositoryPort.searchReservas(criteria, limit, offset);
    }

    private EstadoDisponibilidad calculateDisponibilidad(Recurso recurso, Optional<OffsetDateTime> availabilityDate) {
        if (recurso.getEstado() != null && recurso.getEstado().toString().equals("EN_MANTENIMIENTO")) { // Adjust enum comparison as needed
            return EstadoDisponibilidad.NO_DISPONIBLE;
        }

        if (availabilityDate.isPresent()) {
            boolean isReserved = reservasRepositoryPort.existeReserva(recurso.getId(), availabilityDate.get());
            if (isReserved) {
                return EstadoDisponibilidad.RESERVADO;
            }
        }

        return EstadoDisponibilidad.DISPONIBLE;
    }

    private static class CriteriaSeparationHelper implements CriteriaVisitor<Void> {
        private final List<Criteria> resourceCriteriaList = new ArrayList<>();
        private OffsetDateTime availabilityDate;

        public CriteriaSeparationHelper(Criteria rootCriteria) {
            if (rootCriteria != null) {
                rootCriteria.accept(this);
            }
        }

        public Criteria getResourceCriteria() {
            if (resourceCriteriaList.isEmpty()) {
                return null;
            }
            if (resourceCriteriaList.size() == 1) {
                return resourceCriteriaList.get(0);
            }
            return new AndCriteria(resourceCriteriaList.toArray(new Criteria[0]));
        }

        public Optional<OffsetDateTime> getAvailabilityDate() {
            return Optional.ofNullable(availabilityDate);
        }

        @Override
        public Void visit(AndCriteria criteria) {
            criteria.getCriteria().forEach(c -> c.accept(this));
            return null;
        }

        @Override
        public Void visit(OrCriteria criteria) {
            // For simplicity, OR criteria are considered resource-specific.
            // A more complex implementation could handle OR with availability.
            resourceCriteriaList.add(criteria);
            return null;
        }

        @Override
        public Void visit(DisponibilidadCriteria criteria) {
            if (this.availabilityDate == null) {
                this.availabilityDate = criteria.getFechaHora();
            } // else: multiple availability criteria, we only consider the first one.
            return null;
        }

        private void addResourceCriteria(Criteria criteria) {
            resourceCriteriaList.add(criteria);
        }

        // --- Visit methods for all other resource-specific criteria ---
        @Override
        public Void visit(ar.edu.ceniit.demo.reservas.application.entitys.criteria.field.RecursoNombreCriteria criteria) {
            addResourceCriteria(criteria);
            return null;
        }

        @Override
        public Void visit(ar.edu.ceniit.demo.reservas.application.entitys.criteria.field.RecursoDescripcionCriteria criteria) {
            addResourceCriteria(criteria);
            return null;
        }

        @Override
        public Void visit(ar.edu.ceniit.demo.reservas.application.entitys.criteria.field.RecursoEstadoCriteria criteria) {
            addResourceCriteria(criteria);
            return null;
        }

        @Override
        public Void visit(ar.edu.ceniit.demo.reservas.application.entitys.criteria.field.RecursoIdCriteria criteria) {
            addResourceCriteria(criteria);
            return null;
        }

        @Override
        public Void visit(ar.edu.ceniit.demo.reservas.application.entitys.criteria.field.RecursoCreatedAtCriteria criteria) {
            addResourceCriteria(criteria);
            return null;
        }

        @Override
        public Void visit(ar.edu.ceniit.demo.reservas.application.entitys.criteria.field.RecursoUpdatedAtCriteria criteria) {
            addResourceCriteria(criteria);
            return null;
        }

        @Override
        public Void visit(ar.edu.ceniit.demo.reservas.application.entitys.criteria.field.RecursoHrefPhotoCriteria criteria) {
            addResourceCriteria(criteria);
            return null;
        }
    }
}
