package daw2a.gestion_alimentos_api_rest.repositories;

import daw2a.gestion_alimentos_api_rest.entities.Existencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExistenciaRepository extends JpaRepository<Existencia, Long> {
}