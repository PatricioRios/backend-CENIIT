package ar.edu.ceniit.demo.reservas.domain;

public class RecursoDisponibilidad {
    private final Recurso recurso;
    private final EstadoDisponibilidad estadoDisponibilidad;

    public RecursoDisponibilidad(Recurso recurso, EstadoDisponibilidad estadoDisponibilidad) {
        this.recurso = recurso;
        this.estadoDisponibilidad = estadoDisponibilidad;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public EstadoDisponibilidad getEstadoDisponibilidad() {
        return estadoDisponibilidad;
    }
}
