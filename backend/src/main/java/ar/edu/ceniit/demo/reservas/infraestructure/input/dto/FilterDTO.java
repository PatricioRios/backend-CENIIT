package ar.edu.ceniit.demo.reservas.infraestructure.input.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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
        @JsonSubTypes.Type(value = DisponibilidadFilterDTO.class, name = "DISPONIBILIDAD")
})
public abstract class FilterDTO {
}
