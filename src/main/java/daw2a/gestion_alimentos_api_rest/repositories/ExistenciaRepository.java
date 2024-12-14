package daw2a.gestion_alimentos_api_rest.repositories;

import daw2a.gestion_alimentos_api_rest.entities.Existencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ExistenciaRepository extends JpaRepository<Existencia, Long> {
    Page<Existencia> findByAlimento_IdAndUbicacion_IdOrderByFechaEntradaAsc(Long idAlimento, Long idUbicacion, Pageable pageable);

    Page<Existencia> findByAlimento_IdAndUbicacion_Id(Long idAlimento, Long idUbicacion, Pageable pageable);

    Page<Existencia> findByAlimento_Id(Long id, Pageable pageable);

    Page<Existencia> findByUbicacion_Id(Long id, Pageable pageable);

    Page<Existencia> findByAlimento_FechaCaducidadBetween(LocalDate fechaInicio, LocalDate fechaFin, Pageable pageable);

    Page<Existencia> findByUbicacion_TipoUbicacionIgnoreCase(String tipoUbicacion, Pageable pageable);

}
