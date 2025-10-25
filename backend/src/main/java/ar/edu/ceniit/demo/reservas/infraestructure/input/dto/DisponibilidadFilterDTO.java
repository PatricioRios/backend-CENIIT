package ar.edu.ceniit.demo.reservas.infraestructure.input.dto;

import java.time.OffsetDateTime;

public class DisponibilidadFilterDTO extends FilterDTO {
    private OffsetDateTime fechaHora;

    public OffsetDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(OffsetDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}
