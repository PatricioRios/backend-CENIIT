package ar.edu.ceniit.demo.reservas.infraestructure.input.mapper;

import ar.edu.ceniit.demo.reservas.application.entitys.criteria.*;
import ar.edu.ceniit.demo.reservas.application.entitys.criteria.field.*;
import ar.edu.ceniit.demo.reservas.domain.Recurso;
import ar.edu.ceniit.demo.reservas.domain.RecursoDisponibilidad;
import ar.edu.ceniit.demo.reservas.infraestructure.input.dto.*;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecursoDTOMapper {

    public RecursoResponseDTO toResponse(RecursoDisponibilidad recursoDisponibilidad) {
        Recurso recurso = recursoDisponibilidad.getRecurso();
        RecursoResponseDTO dto = toResponse(recurso);
        dto.setDisponibilidad(recursoDisponibilidad.getEstadoDisponibilidad());
        return dto;
    }

    public RecursoResponseDTO toResponse(Recurso recurso) {
        RecursoResponseDTO dto = new RecursoResponseDTO();
        dto.setId(recurso.getId());
        dto.setNombre(recurso.getNombre());
        dto.setDescripcion(recurso.getDescripcion());
        dto.setHrefPhoto(recurso.getHrefPhoto());
        dto.setEstado(recurso.getEstado());
        dto.setCreatedAt(recurso.getCreatedAt());
        dto.setUpdatedAt(recurso.getUpdatedAt());
        return dto;
    }

    public Criteria toDomain(GetAllRecursosRequest request) {
        if (request == null || request.getFilter() == null) {
            return null; // No filter applied
        }
        return toDomain(request.getFilter());
    }

    private Criteria toDomain(FilterDTO dto) {
        if (dto == null) {
            return null;
        }

        if (dto instanceof AndFilterDTO andFilterDTO) {
            List<Criteria> criteria = andFilterDTO.getFilters().stream()
                    .map(this::toDomain)
                    .collect(Collectors.toList());
            return new AndCriteria(criteria.toArray(new Criteria[0]));
        } else if (dto instanceof OrFilterDTO orFilterDTO) {
            List<Criteria> criteria = orFilterDTO.getFilters().stream()
                    .map(this::toDomain)
                    .collect(Collectors.toList());
            return new OrCriteria(criteria.toArray(new Criteria[0]));
        } else if (dto instanceof StringComparationFieldFilterDTO fieldDto) {
            String value = fieldDto.getValue();
            String op = fieldDto.getOperator().toUpperCase();

            switch (fieldDto.getField()) {
                case NOMBRE:
                    return new RecursoNombreCriteria(StringOperator.valueOf(op), value);
                case DESCRIPCION:
                    return new RecursoDescripcionCriteria(StringOperator.valueOf(op), value);
                case HREF_PHOTO:
                    return new RecursoHrefPhotoCriteria(StringOperator.valueOf(op), value);
                default:
                    throw new IllegalArgumentException("Unsupported field for string filtering: " + fieldDto.getField());
            }
        } else if (dto instanceof NumericComarableFieldFilterDTO fieldDto) {
            String value = fieldDto.getValue();
            String op = fieldDto.getOperator().toUpperCase();

            switch (fieldDto.getField()) {
                case ID:
                    return new RecursoIdCriteria(ComparableOperator.valueOf(op), Long.parseLong(value));
                case ESTADO:
                    // Assuming EstadoRecurso can be mapped from a string value. Adjust if necessary.
                    return new RecursoEstadoCriteria(ComparableOperator.valueOf(op), ar.edu.ceniit.demo.reservas.domain.EstadoRecurso.valueOf(value.toUpperCase()));
                case CREATED_AT:
                    return new RecursoCreatedAtCriteria(ComparableOperator.valueOf(op), OffsetDateTime.parse(value));
                case UPDATED_AT:
                    return new RecursoUpdatedAtCriteria(ComparableOperator.valueOf(op), OffsetDateTime.parse(value));
                default:
                    throw new IllegalArgumentException("Unsupported field for numeric/comparable filtering: " + fieldDto.getField());
            }
        } else if (dto instanceof DisponibilidadFilterDTO disponibilidadFilterDTO) {
            return new DisponibilidadCriteria(disponibilidadFilterDTO.getFechaHora());
        }

        throw new IllegalArgumentException("Unknown FilterDTO type: " + dto.getClass().getName());
    }
}
