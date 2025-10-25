package ar.edu.ceniit.demo.reservas.infraestructure.input.dto;

import ar.edu.ceniit.demo.reservas.domain.Recurso;
import io.swagger.v3.oas.annotations.media.Schema;

public class StringComparationFieldFilterDTO extends FilterDTO {
    @Schema(description = "Field to filter by.",
            example = "NOMBRE",
            allowableValues = {"NOMBRE", "DESCRIPCION", "HREF_PHOTO"})
    private Recurso.Field field;
    @Schema(description = "Operator for the filter. Depends on the field type (String or Comparable).",
            example = "CONTAINS",
            allowableValues = {
                    "EQUAL", "NOT_EQUAL",
                    "LIKE", "STARTS_WITH",
                    "CONTAINS",
            })
    private String operator;
    @Schema(description = "Value to filter by.",
            example = "proyector")
    private String value;

    public Recurso.Field getField() {
        return field;
    }

    public void setField(Recurso.Field field) {
        this.field = field;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
