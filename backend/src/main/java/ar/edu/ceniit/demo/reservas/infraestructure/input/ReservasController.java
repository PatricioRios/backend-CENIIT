package ar.edu.ceniit.demo.reservas.infraestructure.input;

import ar.edu.ceniit.demo.common.entitys.PagedResult;
import ar.edu.ceniit.demo.reservas.application.entitys.criteria.Criteria;
import ar.edu.ceniit.demo.reservas.application.entitys.exceptions.RecursoBadRequestException;
import ar.edu.ceniit.demo.reservas.application.entitys.exceptions.RecursoNotFoundException;
import ar.edu.ceniit.demo.reservas.application.entitys.objects.SortOrder;
import ar.edu.ceniit.demo.reservas.application.ports.ReservasUseCases;
import ar.edu.ceniit.demo.reservas.domain.Recurso;
import ar.edu.ceniit.demo.reservas.domain.RecursoDisponibilidad;
import ar.edu.ceniit.demo.reservas.infraestructure.input.dto.GetAllRecursosRequest;
import ar.edu.ceniit.demo.reservas.infraestructure.input.dto.RecursoResponseDTO;
import ar.edu.ceniit.demo.reservas.infraestructure.input.mapper.RecursoDTOMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import ar.edu.ceniit.demo.reservas.application.entitys.criteria.ComparableOperator;
import ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria.AndReservaCriteria;
import ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria.ReservaCriteria;
import ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria.field.ReservaAprobadorOCanceladorCriteria;
import ar.edu.ceniit.demo.reservas.application.entitys.reserva_criteria.field.ReservaUsuarioSolicitanteCriteria;
import ar.edu.ceniit.demo.reservas.domain.Reserva;
import ar.edu.ceniit.demo.reservas.domain.EstadoReserva;
import ar.edu.ceniit.demo.reservas.infraestructure.input.dto.ReservaResponseDTO;
import ar.edu.ceniit.demo.reservas.infraestructure.input.dto.CreateReservaRequestDTO;
import ar.edu.ceniit.demo.reservas.infraestructure.input.mapper.ReservaDTOMapper;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.ArrayList;

@RestController
@RequestMapping("/recursos")
public class ReservasController {

    private final ReservasUseCases reservasUseCases;
    private final RecursoDTOMapper recursoDTOMapper;
    private final ReservaDTOMapper reservaDTOMapper;

    public ReservasController(ReservasUseCases reservasUseCases, RecursoDTOMapper recursoDTOMapper, ReservaDTOMapper reservaDTOMapper) {
        this.reservasUseCases = reservasUseCases;
        this.recursoDTOMapper = recursoDTOMapper;
        this.reservaDTOMapper = reservaDTOMapper;
    }

    @GetMapping("/{id}/agenda")
    public ResponseEntity<PagedResult<ReservaResponseDTO>> getAgenda(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fechaDesde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fechaHasta,
            @RequestParam(required = false) Integer solicitanteId,
            @RequestParam(required = false) Integer aprobadorId,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit) throws RecursoBadRequestException {

        if (fechaDesde.isAfter(fechaHasta)) {
            throw new RecursoBadRequestException("La fecha desde no puede ser posterior a la fecha hasta");
        }

        List<ReservaCriteria> criteriaList = new ArrayList<>();
        if (solicitanteId != null) {
            criteriaList.add(new ReservaUsuarioSolicitanteCriteria(ComparableOperator.EQUAL, solicitanteId));
        }
        if (aprobadorId != null) {
            criteriaList.add(new ReservaAprobadorOCanceladorCriteria(ComparableOperator.EQUAL, aprobadorId));
        }

        ReservaCriteria criteria = null;
        if (!criteriaList.isEmpty()) {
            criteria = new AndReservaCriteria(criteriaList.toArray(new ReservaCriteria[0]));
        }

        PagedResult<Reserva> pagedResult = reservasUseCases.getReservasForRecurso(id, fechaDesde, fechaHasta, criteria, limit, offset);

        List<ReservaResponseDTO> dtoList = pagedResult.getContent().stream()
                .map(reservaDTOMapper::toDTO)
                .collect(Collectors.toList());

        PagedResult<ReservaResponseDTO> response = new PagedResult<>(
                dtoList,
                pagedResult.getTotalElements(),
                pagedResult.getNumber(),
                pagedResult.getSize(),
                pagedResult.getNumberOfElements(),
                pagedResult.isFirst(),
                pagedResult.isLast(),
                pagedResult.isEmpty()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{recursoId}/reservas")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReservaResponseDTO> createReserva(
            @PathVariable Long recursoId,
            @Valid @RequestBody CreateReservaRequestDTO request) throws RecursoBadRequestException {
        
        // TODO: Obtener ID del usuario autenticado del token
        // Por ahora usaremos un ID temporal para que compile
        Integer userId = 1; // TEMPORAL - debe venir del token JWT
        
        Reserva reserva = new Reserva(
            null,
            request.getNombre(),
            request.getDescripcion(),
            request.getFechaHoraInicio(),
            request.getFechaHoraFin(),
            EstadoReserva.PENDIENTE,
            recursoId.intValue(),
            userId,
            null
        );
        
        Reserva created = reservasUseCases.createReserva(reserva, userId);
        ReservaResponseDTO response = reservaDTOMapper.toDTO(created);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/reservas/{id}/aprobar")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReservaResponseDTO> aprobarReserva(@PathVariable Integer id) {
        
        // TODO: Obtener ID del aprobador del token
        Integer approverId = 1; // TEMPORAL
        
        Reserva approved = reservasUseCases.aprovarReserva(id, approverId);
        ReservaResponseDTO response = reservaDTOMapper.toDTO(approved);
        
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/reservas/{id}/cancelar")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReservaResponseDTO> cancelarReserva(@PathVariable Integer id) {
        
        // TODO: Obtener ID del usuario del token y validar propiedad
        Integer userId = 1; // TEMPORAL
        
        Reserva cancelled = reservasUseCases.cancelarReserva(id, userId);
        ReservaResponseDTO response = reservaDTOMapper.toDTO(cancelled);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecursoResponseDTO> getRecursoById(@PathVariable Long id) throws RecursoNotFoundException {
        // TODO: Implement this method
        throw new RecursoNotFoundException("Recurso not found");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/search")
    @Operation(
            summary = "Search recursos with optional filtering, sorting, and pagination",
            description = "Returns a list of recursos based on complex filter criteria."
    )
    public ResponseEntity<PagedModel<EntityModel<RecursoResponseDTO>>> searchRecursos(
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "10") Integer limit,
            @RequestParam(required = false, defaultValue = "NOMBRE") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") String sortOrder,
            @RequestBody(required = false) GetAllRecursosRequest filterRequest
    ) throws RecursoBadRequestException {
        Criteria criteria = recursoDTOMapper.toDomain(filterRequest);
        SortOrder order = new SortOrder(stringToField(sortBy), stringToOrder(sortOrder));

        PagedResult<RecursoDisponibilidad> recursoPage = reservasUseCases.getAllRecursos(criteria, order, limit, offset);

        List<EntityModel<RecursoResponseDTO>> recursoResponseDTOs = recursoPage.getContent().stream()
                .map(recursoDTOMapper::toResponse)
                .map(recurso -> {
                    try {
                        return EntityModel.of(recurso,
                                linkTo(methodOn(ReservasController.class).getRecursoById(recurso.getId().longValue())).withSelfRel());
                    } catch (RecursoNotFoundException e) {
                        // This should not happen if the resource exists
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(
                recursoPage.getSize(),
                recursoPage.getNumber(),
                recursoPage.getTotalElements(),
                recursoPage.getTotalPages()
        );

        PagedModel<EntityModel<RecursoResponseDTO>> response = PagedModel.of(recursoResponseDTOs, pageMetadata);

        response.add(linkTo(methodOn(ReservasController.class).searchRecursos(offset, limit, sortBy, sortOrder, filterRequest)).withSelfRel());

        if (!recursoPage.isLast()) {
            response.add(linkTo(methodOn(ReservasController.class).searchRecursos(offset + limit, limit, sortBy, sortOrder, filterRequest)).withRel("next"));
        }

        if (!recursoPage.isFirst()) {
            response.add(linkTo(methodOn(ReservasController.class).searchRecursos(offset - limit, limit, sortBy, sortOrder, filterRequest)).withRel("prev"));
        }

        return ResponseEntity.ok(response);
    }

    private SortOrder.Order stringToOrder(String order) throws RecursoBadRequestException {
        try {
            return SortOrder.Order.valueOf(order.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RecursoBadRequestException("Invalid sort order: " + order);
        }
    }

    private Recurso.Field stringToField(String fieldName) throws RecursoBadRequestException {
        try {
            return Recurso.Field.valueOf(fieldName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RecursoBadRequestException("Invalid field name: " + fieldName);
        }
    }
}
