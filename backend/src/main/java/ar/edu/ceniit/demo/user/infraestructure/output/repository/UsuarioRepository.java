package ar.edu.ceniit.demo.user.infraestructure.output.repository;

import ar.edu.ceniit.demo.user.infraestructure.output.schema.UserEntityTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<UserEntityTable, UUID> {
    Optional<UserEntityTable> findByUuid(UUID uuid);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUuid(UUID uuid);

    @Transactional
    void deleteByUuid(UUID uuid);
}
