package ar.edu.ceniit.demo.user.infraestructure.input.dto;

/**
 * DTO principal para la solicitud de getAllUsers.
 * Contiene el filtro raíz que se aplicará.
 */
public class GetAllUsersRequest {
    private FilterDTO filter;

    public FilterDTO getFilter() {
        return filter;
    }

    public void setFilter(FilterDTO filter) {
        this.filter = filter;
    }
}