package ar.edu.ceniit.demo.reservas.domain;

import java.time.OffsetDateTime;

public class Reserva {
    private Integer id;
    private String nombre;
    private String descripcion;

    private OffsetDateTime fechaHoraInicio;
    private OffsetDateTime fechaHoraFin;
    private EstadoReserva estado;
    private Integer recursoSolicitadoId;
    private Integer usuarioSolicitanteId;
    private Integer aprobadorOCanceladorId;

    public Reserva(Integer id, String nombre, String descripcion, OffsetDateTime fechaHoraInicio, OffsetDateTime fechaHoraFin, EstadoReserva estado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.estado = estado;
    }
    public Reserva(Integer id, Integer aprobadorOCanceladorId, EstadoReserva estado){
        this.id = id;
        this.aprobadorOCanceladorId = aprobadorOCanceladorId;
        this.estado = estado;
    }

    public Reserva(Integer id, String nombre, String descripcion, OffsetDateTime fechaHoraInicio, OffsetDateTime fechaHoraFin, EstadoReserva estado, Integer recursoSolicitadoId, Integer usuarioSolicitanteId, Integer aprobadorOCanceladorId) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.estado = estado;
        this.recursoSolicitadoId = recursoSolicitadoId;
        this.usuarioSolicitanteId = usuarioSolicitanteId;
        this.aprobadorOCanceladorId = aprobadorOCanceladorId;
    }

    public Integer getUsuarioSolicitanteId() {
        return usuarioSolicitanteId;
    }

    public void setUsuarioSolicitanteId(Integer usuarioSolicitanteId) {
        this.usuarioSolicitanteId = usuarioSolicitanteId;
    }

    public Integer getAprobadorOCanceladorId() {
        return aprobadorOCanceladorId;
    }

    public void setAprobadorOCanceladorId(Integer aprobadorOCanceladorId) {
        this.aprobadorOCanceladorId = aprobadorOCanceladorId;
    }

    public Integer getRecursoSolicitadoId() {
        return recursoSolicitadoId;
    }

    public void setRecursoSolicitadoId(Integer recursoSolicitadoId) {
        this.recursoSolicitadoId = recursoSolicitadoId;
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

    public OffsetDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(OffsetDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public OffsetDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(OffsetDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }
}
