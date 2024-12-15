package daw2a.gestion_alimentos_api_rest.repositories;

import daw2a.gestion_alimentos_api_rest.entities.Existencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

/**
 * Repositorio para gestionar las operaciones CRUD relacionadas con la entidad {@link Existencia}.
 * <p>Este repositorio extiende {@link JpaRepository}, proporcionando acceso a los métodos básicos de persistencia
 * y permitiendo la definición de consultas personalizadas para buscar existencias en función de diversos criterios,
 * como el alimento, la ubicación o la fecha de caducidad.</p>
 */
public interface ExistenciaRepository extends JpaRepository<Existencia, Long> {

    /**
     * Busca las existencias de un alimento en una ubicación específica, ordenadas por la fecha de entrada ascendente.
     * <p>Este método permite obtener las existencias de un alimento en una ubicación particular, ordenadas por la
     * fecha en la que fueron ingresadas.</p>
     *
     * @param idAlimento El ID del alimento.
     * @param idUbicacion El ID de la ubicación.
     * @param pageable Los parámetros de paginación.
     * @return Una página de existencias para el alimento y la ubicación especificados, ordenadas por fecha de entrada.
     */
    Page<Existencia> findByAlimento_IdAndUbicacion_IdOrderByFechaEntradaAsc(Long idAlimento, Long idUbicacion, Pageable pageable);

    /**
     * Busca las existencias de un alimento en una ubicación específica sin ordenarlas por fecha.
     * <p>Este método permite obtener las existencias de un alimento en una ubicación particular.</p>
     *
     * @param idAlimento El ID del alimento.
     * @param idUbicacion El ID de la ubicación.
     * @param pageable Los parámetros de paginación.
     * @return Una página de existencias para el alimento y la ubicación especificados.
     */
    Page<Existencia> findByAlimento_IdAndUbicacion_Id(Long idAlimento, Long idUbicacion, Pageable pageable);

    /**
     * Busca las existencias de un alimento sin importar la ubicación.
     * <p>Este método permite obtener todas las existencias de un alimento específico en todas las ubicaciones.</p>
     *
     * @param id El ID del alimento.
     * @param pageable Los parámetros de paginación.
     * @return Una página de existencias para el alimento especificado.
     */
    Page<Existencia> findByAlimento_Id(Long id, Pageable pageable);

    /**
     * Busca las existencias en una ubicación específica sin importar el alimento.
     * <p>Este método permite obtener todas las existencias en una ubicación determinada, sin importar el tipo de alimento.</p>
     *
     * @param id El ID de la ubicación.
     * @param pageable Los parámetros de paginación.
     * @return Una página de existencias para la ubicación especificada.
     */
    Page<Existencia> findByUbicacion_Id(Long id, Pageable pageable);

    /**
     * Busca las existencias de un alimento cuya fecha de caducidad esté dentro de un rango específico.
     * <p>Este método permite realizar búsquedas de existencias cuyo alimento tiene una fecha de caducidad dentro de un
     * intervalo de fechas dado.</p>
     *
     * @param fechaInicio La fecha de inicio del rango.
     * @param fechaFin La fecha de fin del rango.
     * @param pageable Los parámetros de paginación.
     * @return Una página de existencias de alimentos con fechas de caducidad dentro del rango especificado.
     */
    Page<Existencia> findByAlimento_FechaCaducidadBetween(LocalDate fechaInicio, LocalDate fechaFin, Pageable pageable);

    /**
     * Busca las existencias en ubicaciones de un tipo específico.
     * <p>Este método permite obtener las existencias en ubicaciones de un tipo determinado, como "alacena", "nevera" o "congelador".</p>
     *
     * @param tipoUbicacion El tipo de ubicación que se busca (por ejemplo, "ALACENA", "NEVERA", "CONGELADOR").
     * @param pageable Los parámetros de paginación.
     * @return Una página de existencias en las ubicaciones del tipo especificado.
     */
    Page<Existencia> findByUbicacion_TipoUbicacionIgnoreCase(String tipoUbicacion, Pageable pageable);

}
