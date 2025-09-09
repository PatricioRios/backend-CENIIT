package ar.edu.ceniit.demo.user.infraestructure.input.dto;

import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;

/**
 * DTO para un filtro simple sobre un campo específico.
 * Contiene el campo, el operador y el valor para la comparación.
 */
public class FieldFilterDTO extends FilterDTO {
    private User.Field field;
    private String operator; // Ej: EQUALS, CONTAINS, GREATER_THAN
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
