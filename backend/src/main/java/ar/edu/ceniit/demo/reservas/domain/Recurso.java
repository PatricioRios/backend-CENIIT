package ar.edu.ceniit.demo.reservas.domain;

import ar.edu.ceniit.demo.reservas.domain.EstadoRecurso;

import java.time.OffsetDateTime;

/**
 * Modelo de datos (POJO) que representa un Recurso.
 * No incluye anotaciones de persistencia.
 */
public class Recurso {

    public enum Field {
        ID, NOMBRE, DESCRIPCION, HREF_PHOTO, ESTADO, CREATED_AT, UPDATED_AT
    }

    private Integer id;
    private String nombre;
    private String descripcion;
    private String hrefPhoto;
    private EstadoRecurso estado;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    // --- Constructores ---

    /**
     * Constructor vacío.
     */
    public Recurso() {
    }

    /**
     * Constructor con todos los campos.
     */
    public Recurso(Integer id, String nombre, String descripcion, String hrefPhoto, EstadoRecurso estado, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.hrefPhoto = hrefPhoto;
        this.estado = estado;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // --- Getters y Setters ---

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

    // --- Método toString ---

    @Override
    public String toString() {
        return "Recurso{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", hrefPhoto='" + hrefPhoto + '\'' +
                ", estado=" + estado +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}