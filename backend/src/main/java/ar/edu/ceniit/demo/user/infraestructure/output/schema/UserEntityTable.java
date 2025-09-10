package ar.edu.ceniit.demo.user.infraestructure.output.schema;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "usuario")
@Getter
@Setter
public class UserEntityTable {

    // ✅ El UUID es la única clave primaria. Simple y robusto.
    @Id
    @Column(nullable = false)
    private UUID uuid;

    // ✅ El 'id' ahora es una columna normal, no una clave.
    // Le decimos a Hibernate cómo usar la secuencia de PostgreSQL.
    @Generated(value = GenerationTime.INSERT)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id; // Usamos Long para consistencia

    @Column(length = 200, nullable = false, unique = true)
    private String email;

    // ... el resto de tus campos (dni, username, nombre, etc.)
    @Column(unique = true)
    private Integer dni;

    @Column(length = 200, nullable = false, unique = true)
    private String username;

    @Column(name = "nombre", length = 200, nullable = false)
    private String nombre;

    @Column(name = "segundo_nombre", length = 200)
    private String segundoNombre;

    @Column(name = "apellido", length = 200, nullable = false)
    private String apellido;

    @Column(name = "segundo_apellido", length = 200)
    private String segundoApellido;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    // Asignamos el UUID antes de que la entidad se guarde por primera vez
    @PrePersist
    public void prePersist() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }
}