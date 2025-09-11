package ar.edu.ceniit.demo.user.infraestructure.input.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Clase base para los DTO de filtro.
 * Utiliza anotaciones de Jackson para manejar la herencia y poder recibir
 * diferentes tipos de filtros (AND, OR, FIELD) en el mismo campo JSON.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AndFilterDTO.class, name = "AND"),
        @JsonSubTypes.Type(value = OrFilterDTO.class, name = "OR"),
        @JsonSubTypes.Type(value = StringComparationFieldFilterDTO.class, name = "STRING_FIELD"),
        @JsonSubTypes.Type(value = NumericComarableFieldFilterDTO.class, name = "NUMERIC_FIELD"),

})
public abstract class FilterDTO {
}
