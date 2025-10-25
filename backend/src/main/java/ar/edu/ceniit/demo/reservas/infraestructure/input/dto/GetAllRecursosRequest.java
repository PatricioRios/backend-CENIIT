package ar.edu.ceniit.demo.reservas.infraestructure.input.dto;

public class GetAllRecursosRequest {
    private FilterDTO filter;

    public FilterDTO getFilter() {
        return filter;
    }

    public void setFilter(FilterDTO filter) {
        this.filter = filter;
    }
}
