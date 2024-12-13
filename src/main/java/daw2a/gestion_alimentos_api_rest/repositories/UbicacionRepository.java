package daw2a.gestion_alimentos_api_rest.repositories;

import daw2a.gestion_alimentos_api_rest.entities.Ubicacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UbicacionRepository extends JpaRepository<Ubicacion, Long> {
    Page<Ubicacion> findByTipoUbicacionContainingIgnoreCase(String tipoUbicacion, Pageable pageable);
}
