package daw2a.gestion_alimentos_api_rest.repositories;

import daw2a.gestion_alimentos_api_rest.entities.Alimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

/**
 * Repositorio para gestionar las operaciones CRUD relacionadas con la entidad {@link Alimento}.
 * <p>Este repositorio extiende {@link JpaRepository}, proporcionando acceso a los métodos básicos de persistencia
 * y permitiendo la definición de consultas personalizadas para buscar alimentos en función de su nombre o fecha de caducidad.</p>
 */
public interface AlimentoRepository extends JpaRepository<Alimento, Long> {

    /**
     * Busca alimentos cuyo nombre contenga una cadena de texto, sin distinguir entre mayúsculas y minúsculas.
     * <p>Este método permite realizar búsquedas parciales por el nombre del alimento en el sistema.</p>
     *
     * @param name El nombre del alimento que se busca (puede ser parcial).
     * @param pageable Los parámetros de paginación.
     * @return Una página de alimentos cuyo nombre contenga la cadena proporcionada.
     */
    Page<Alimento> findByNombreContainingIgnoreCase(String name, Pageable pageable);

    /**
     * Busca alimentos cuya fecha de caducidad esté dentro de un rango de fechas.
     * <p>Este método permite realizar búsquedas de alimentos que caducan dentro de un intervalo de tiempo.</p>
     *
     * @param fechaInicio La fecha de inicio del rango de búsqueda.
     * @param fechaFin La fecha de fin del rango de búsqueda.
     * @param pageable Los parámetros de paginación.
     * @return Una página de alimentos cuya fecha de caducidad esté dentro del rango especificado.
     */
    Page<Alimento> findByFechaCaducidadBetween(LocalDate fechaInicio,  LocalDate fechaFin, Pageable pageable);
}
