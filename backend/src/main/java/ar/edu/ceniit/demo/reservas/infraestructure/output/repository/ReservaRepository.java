package ar.edu.ceniit.demo.reservas.infraestructure.output.repository;

import ar.edu.ceniit.demo.reservas.infraestructure.output.schema.ReservaEntityTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntityTable, Integer>, JpaSpecificationExecutor<ReservaEntityTable> {
}
