package ar.edu.ceniit.demo.reservas.infraestructure.output;

import ar.edu.ceniit.demo.common.entitys.PagedResult;
import ar.edu.ceniit.demo.reservas.application.entitys.criteria.Criteria;
import ar.edu.ceniit.demo.reservas.application.entitys.objects.SortOrder;
import ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria.*;
import ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria.field.*;
import ar.edu.ceniit.demo.reservas.application.ports.ReservasOutputPort;
import ar.edu.ceniit.demo.reservas.application.ports.ReservasRepositoryPort;
import ar.edu.ceniit.demo.reservas.domain.Recurso;
import ar.edu.ceniit.demo.reservas.domain.Reserva;
import ar.edu.ceniit.demo.reservas.infraestructure.output.mapper.ReservaMapper;
import ar.edu.ceniit.demo.reservas.infraestructure.output.repository.ReservaRepository;
import ar.edu.ceniit.demo.reservas.infraestructure.output.schema.ReservaEntityTable;
import ar.edu.ceniit.demo.reservas.infraestructure.output.visitor.ReservaSpecificationVisitor;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReservasPersistenceAdapter implements ReservasRepositoryPort, ReservasOutputPort {

    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;


    @Override
    public boolean existeReserva(Integer recursoId, OffsetDateTime fechaHora) {
        Specification<ReservaEntityTable> spec = (root, query, builder) -> {
            Predicate recursoMatch = builder.equal(root.get("recursoSolicitadoId"), recursoId);
            Predicate noEstaCancelada = builder.notEqual(root.get("estado"), ar.edu.ceniit.demo.reservas.domain.EstadoReserva.CANCELADA);
            Predicate inicioAntesDeFecha = builder.lessThanOrEqualTo(root.get("fechaHoraInicio"), fechaHora);
            Predicate finDespuesDeFecha = builder.greaterThanOrEqualTo(root.get("fechaHoraFin"), fechaHora);

            return builder.and(recursoMatch, noEstaCancelada, inicioAntesDeFecha, finDespuesDeFecha);
        };

        return reservaRepository.count(spec) > 0;
    }

    @Override
    public boolean existeReservaInRange(Integer recursoId, OffsetDateTime fechaHoraInicio, OffsetDateTime fechaHoraFin) {
        Specification<ReservaEntityTable> spec = (root, query, builder) -> {
            Predicate recursoMatch = builder.equal(root.get("recursoSolicitadoId"), recursoId);
            Predicate noEstaCancelada = builder.notEqual(root.get("estado"), ar.edu.ceniit.demo.reservas.domain.EstadoReserva.CANCELADA);
            Predicate finDespuesDeNuevoInicio = builder.greaterThan(root.get("fechaHoraFin"), fechaHoraInicio);
            Predicate inicioAntesDenNuevoFin = builder.lessThan(root.get("fechaHoraInicio"), fechaHoraFin);

            return builder.and(recursoMatch, noEstaCancelada, finDespuesDeNuevoInicio, inicioAntesDenNuevoFin);
        };

        return reservaRepository.count(spec) > 0;
    }

    @Override
    public Reserva createReserva(Reserva reserva, Integer solicitanteId) {
        ReservaEntityTable entity = reservaMapper.toEntity(reserva);

        entity.setSolicitanteId(solicitanteId);
        entity.setEstado(reserva.getEstado() != null ? reserva.getEstado() : ar.edu.ceniit.demo.reservas.domain.EstadoReserva.PENDIENTE);

        ReservaEntityTable savedEntity = reservaRepository.save(entity);

        return reservaMapper.toDomain(savedEntity);
    }

    @Override
    public Reserva updateReserva(Reserva reserva) {
        ReservaEntityTable existing = reservaRepository.findById(reserva.getId())
            .orElseThrow(() -> new ar.edu.ceniit.demo.reservas.application.entitys.exceptions.ReservaNotFoundException(
                "Reserva con ID " + reserva.getId() + " no encontrada"));

        if (reserva.getEstado() != null) {
            existing.setEstado(reserva.getEstado());
        }

        if (reserva.getAprobadorOCanceladorId() != null) {
            existing.setAprobadorOCanceladorId(reserva.getAprobadorOCanceladorId());
        }

        ReservaEntityTable updated = reservaRepository.save(existing);

        return reservaMapper.toDomain(updated);
    }


    @Override
    public PagedResult<Reserva> searchReservas(ReservaCriteria criteria, int limit, int offset) {
        Specification<ReservaEntityTable> finalSpec = Specification.where(null);

        if (criteria != null) {
            ReservaCriteriaSeparationHelper separationHelper = new ReservaCriteriaSeparationHelper(criteria);
            ReservaCriteria otherCriteria = separationHelper.getOtherCriteria();

            Specification<ReservaEntityTable> criteriaSpec = otherCriteria != null ? otherCriteria.accept(new ReservaSpecificationVisitor()) : null;

            if (criteriaSpec != null) {
                finalSpec = finalSpec.and(criteriaSpec);
            }

            Optional<OffsetDateTime> fechaDesde = separationHelper.getFechaDesde();
            Optional<OffsetDateTime> fechaHasta = separationHelper.getFechaHasta();

            // The original logic was looking for reservations contained within the range.
            // The new logic should find reservations that overlap with the range.
            // Overlapping condition: (r.fechaHoraInicio < fechaHasta AND r.fechaHoraFin > fechaDesde)
            if (fechaDesde.isPresent() && fechaHasta.isPresent()) {
                Specification<ReservaEntityTable> dateRangeSpec = (root, query, builder) ->
                        builder.and(
                                builder.lessThan(root.get("fechaHoraInicio"), fechaHasta.get()),
                                builder.greaterThan(root.get("fechaHoraFin"), fechaDesde.get())
                        );
                finalSpec = finalSpec.and(dateRangeSpec);
            } else if (fechaDesde.isPresent()) {
                Specification<ReservaEntityTable> dateRangeSpec = (root, query, builder) ->
                        builder.greaterThan(root.get("fechaHoraFin"), fechaDesde.get());
                finalSpec = finalSpec.and(dateRangeSpec);
            } else if (fechaHasta.isPresent()) {
                Specification<ReservaEntityTable> dateRangeSpec = (root, query, builder) ->
                        builder.lessThan(root.get("fechaHoraInicio"), fechaHasta.get());
                finalSpec = finalSpec.and(dateRangeSpec);
            }
        }

        int page = offset / limit;
        Pageable pageable = PageRequest.of(page, limit);

        Page<ReservaEntityTable> entityPage = reservaRepository.findAll(finalSpec, pageable);

        List<Reserva> reservas = entityPage.getContent().stream()
                .map(reservaMapper::toDomain)
                .collect(Collectors.toList());

        return new PagedResult<>(
                reservas,
                entityPage.getTotalElements(),
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.getNumberOfElements(),
                entityPage.isFirst(),
                entityPage.isLast(),
                entityPage.isEmpty()
        );
    }

    @Override
    public PagedResult<Recurso> findByCriteria(Criteria criteria, SortOrder sortOrder, int limit, int offset) {
        // This method belongs to ReservasOutputPort and is not part of this refactoring.
        return null;
    }

    private static class ReservaCriteriaSeparationHelper implements ReservaCriteriaVisitor<Void> {
        private final List<ReservaCriteria> otherCriteriaList = new ArrayList<>();
        private OffsetDateTime fechaDesde;
        private OffsetDateTime fechaHasta;

        public ReservaCriteriaSeparationHelper(ReservaCriteria rootCriteria) {
            if (rootCriteria != null) {
                rootCriteria.accept(this);
            }
        }

        public ReservaCriteria getOtherCriteria() {
            if (otherCriteriaList.isEmpty()) {
                return null;
            }
            if (otherCriteriaList.size() == 1) {
                return otherCriteriaList.get(0);
            }
            return new AndReservaCriteria(otherCriteriaList.toArray(new ReservaCriteria[0]));
        }

        public Optional<OffsetDateTime> getFechaDesde() {
            return Optional.ofNullable(fechaDesde);
        }

        public Optional<OffsetDateTime> getFechaHasta() {
            return Optional.ofNullable(fechaHasta);
        }

        @Override
        public Void visit(AndReservaCriteria criteria) {
            criteria.getCriteria().forEach(c -> c.accept(this));
            return null;
        }

        @Override
        public Void visit(OrReservaCriteria criteria) {
            otherCriteriaList.add(criteria);
            return null;
        }

        @Override
        public Void visit(ReservaFechaDesdeCriteria criteria) {
            if (this.fechaDesde == null) {
                this.fechaDesde = criteria.getValue();
            }
            return null;
        }

        @Override
        public Void visit(ReservaFechaHastaCriteria criteria) {
            if (this.fechaHasta == null) {
                this.fechaHasta = criteria.getValue();
            }
            return null;
        }

        @Override
        public Void visit(ReservaUsuarioSolicitanteCriteria criteria) {
            otherCriteriaList.add(criteria);
            return null;
        }

        @Override
        public Void visit(ReservaAprobadorOCanceladorCriteria criteria) {
            otherCriteriaList.add(criteria);
            return null;
        }

        @Override
        public Void visit(ReservaRecursoIdCriteria criteria) {
            otherCriteriaList.add(criteria);
            return null;
        }
    }
}