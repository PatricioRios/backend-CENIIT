package ar.edu.ceniit.demo.reservas.infraestructure.input.dto;

import ar.edu.ceniit.demo.reservas.domain.Recurso;
import io.swagger.v3.oas.annotations.media.Schema;

public class NumericComarableFieldFilterDTO extends FilterDTO {
    @Schema(description = "Field to filter by.",
            example = "ID",
            allowableValues = {"ID", "ESTADO", "CREATED_AT", "UPDATED_AT"})
    private Recurso.Field field;
    @Schema(description = "Operator for the filter. Depends on the field type (String or Comparable).",
            example = "EQUAL",
            allowableValues = {
                    "EQUAL", "NOT_EQUAL",
                    "GREATER_THAN", "LESS_THAN",
                    "GREATER_THAN_OR_EQUAL", "LESS_THAN_OR_EQUAL"
            })
    private String operator;
    @Schema(description = "Value to filter by. For date fields, use ISO 8601 format (e.g., '2025-09-09T10:00:00Z').",
            example = "1")
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
