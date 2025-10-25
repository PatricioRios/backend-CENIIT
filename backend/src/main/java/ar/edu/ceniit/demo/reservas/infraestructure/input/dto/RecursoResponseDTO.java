package ar.edu.ceniit.demo.reservas.infraestructure.input.dto;

import ar.edu.ceniit.demo.reservas.domain.EstadoRecurso;
import ar.edu.ceniit.demo.reservas.domain.EstadoDisponibilidad;
import lombok.Builder;
import org.springframework.hateoas.server.core.Relation;

import java.time.OffsetDateTime;

@Builder
@Relation(collectionRelation = "recursos")
public class RecursoResponseDTO {

    private Integer id;
    private String nombre;
    private String descripcion;
    private String hrefPhoto;
    private EstadoRecurso estado;
    private EstadoDisponibilidad disponibilidad;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public RecursoResponseDTO() {
    }

    public RecursoResponseDTO(Integer id, String nombre, String descripcion, String hrefPhoto, EstadoRecurso estado, EstadoDisponibilidad disponibilidad, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.hrefPhoto = hrefPhoto;
        this.estado = estado;
        this.disponibilidad = disponibilidad;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHrefPhoto() {
        return hrefPhoto;
    }

    public void setHrefPhoto(String hrefPhoto) {
        this.hrefPhoto = hrefPhoto;
    }

    public EstadoRecurso getEstado() {
        return estado;
    }

    public void setEstado(EstadoRecurso estado) {
        this.estado = estado;
    }

    public EstadoDisponibilidad getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(EstadoDisponibilidad disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
