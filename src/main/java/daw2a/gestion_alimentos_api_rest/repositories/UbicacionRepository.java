package daw2a.gestion_alimentos_api_rest.repositories;

import daw2a.gestion_alimentos_api_rest.entities.Ubicacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para gestionar las operaciones CRUD relacionadas con la entidad {@link Ubicacion}.
 * <p>Este repositorio extiende {@link JpaRepository}, proporcionando acceso a los métodos básicos de persistencia
 * y permitiendo la definición de consultas personalizadas para buscar ubicaciones en función del tipo de ubicación.</p>
 */
public interface UbicacionRepository extends JpaRepository<Ubicacion, Long> {

    /**
     * Busca las ubicaciones cuyo tipo contiene una cadena específica, ignorando mayúsculas y minúsculas.
     * <p>Este método permite realizar búsquedas de ubicaciones que coincidan parcialmente con un tipo de ubicación, sin
     * tener en cuenta las mayúsculas o minúsculas en la comparación.</p>
     *
     * @param tipoUbicacion El tipo de ubicación que se busca (puede ser una coincidencia parcial).
     * @param pageable Los parámetros de paginación.
     * @return Una página de ubicaciones cuyo tipo contiene la cadena especificada, ignorando mayúsculas y minúsculas.
     */
    Page<Ubicacion> findByTipoUbicacionContainingIgnoreCase(String tipoUbicacion, Pageable pageable);
}
