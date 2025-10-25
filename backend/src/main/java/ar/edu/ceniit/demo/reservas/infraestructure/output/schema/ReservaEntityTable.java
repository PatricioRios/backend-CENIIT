package ar.edu.ceniit.demo.reservas.infraestructure.output.schema;

import ar.edu.ceniit.demo.reservas.domain.EstadoReserva;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "reserva", schema = "reservas_schema")
@Getter
@Setter
public class ReservaEntityTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", length = 200)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoReserva estado;

    @Column(name = "solicitante_id", nullable = false)
    private Integer solicitanteId;

    @Column(name = "aprobador_o_cancelador_id")
    private Integer aprobadorOCanceladorId;

    @Column(name = "recurso_solicitado_id")
    private Integer recursoSolicitadoId;

    @Column(name = "fecha_hora_inicio")
    private OffsetDateTime fechaHoraInicio;

    @Column(name = "fecha_hora_fin")
    private OffsetDateTime fechaHoraFin;

    @Column(name = "created_at")
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private OffsetDateTime updatedAt;
}
