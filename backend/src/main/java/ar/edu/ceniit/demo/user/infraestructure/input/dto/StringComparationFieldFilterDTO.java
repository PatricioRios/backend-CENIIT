package ar.edu.ceniit.demo.user.infraestructure.input.dto;

import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for a simple filter on a specific field.
 * Contains the field, operator, and value for the comparison.
 */
public class StringComparationFieldFilterDTO extends FilterDTO {
    @Schema(description = "Field to filter by.",
            example = "CREATED_AT",
            allowableValues = {"CREATED_AT", "UPDATED_AT"})
    private User.Field field;
    @Schema(description = "Operator for the filter. Depends on the field type (String or Comparable).",
            example = "CONTAINS",
            allowableValues = {
                    "EQUAL", "NOT_EQUAL",
                    "LIKE", "STARTS_WITH",
                    "CONTAINS",
    })
    private String operator; // Ej: EQUALS, CONTAINS, GREATER_THAN
    @Schema(description = "Value to filter by. For date fields, use ISO 8601 format (e.g., '2025-09-09T10:00:00Z').",
            example = "john.doe")
    private String value;

    public User.Field getField() {
        return field;
    }

    public void setField(User.Field field) {
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
