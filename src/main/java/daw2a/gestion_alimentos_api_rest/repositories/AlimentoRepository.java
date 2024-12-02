package daw2a.gestion_alimentos_api_rest.repositories;

import daw2a.gestion_alimentos_api_rest.entities.Alimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface AlimentoRepository extends JpaRepository<Alimento, Long> {
    Page<Alimento> findByNombreContainingIgnoreCase(String name, Pageable pageable);
    Page<Alimento> findByFechaCaducidadBetween(LocalDate fechaInicio,  LocalDate fechaFin, Pageable pageable);
}
