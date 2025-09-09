package ar.edu.ceniit.demo.user.infraestructure.input.dto;

import java.util.List;

/**
 * DTO base para filtros compuestos (AND, OR) que contienen una lista de otros filtros.
 */
public abstract class CompositeFilterDTO extends FilterDTO {
    private List<FilterDTO> filters;

    public List<FilterDTO> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterDTO> filters) {
        this.filters = filters;
    }
}
