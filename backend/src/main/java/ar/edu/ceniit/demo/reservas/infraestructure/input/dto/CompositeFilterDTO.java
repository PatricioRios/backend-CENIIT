package ar.edu.ceniit.demo.reservas.infraestructure.input.dto;

import java.util.List;

public abstract class CompositeFilterDTO extends FilterDTO {
    private List<FilterDTO> filters;

    public List<FilterDTO> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterDTO> filters) {
        this.filters = filters;
    }
}
