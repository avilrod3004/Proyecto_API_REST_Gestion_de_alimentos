package daw2a.gestion_alimentos_api_rest.dto.existencia;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data Transfer Object (DTO) utilizado para mover una existencia de un alimento de una ubicación a otra.
 * <p>Este DTO se usa cuando se desea cambiar la ubicación de una existencia dentro del sistema.</p>
 */
@Data
public class MoverExistenciaDTO {

    /**
     * Identificador de la nueva ubicación donde se moverá la existencia del alimento.
     * <p>Este campo es obligatorio y especifica el id de la ubicación destino para el alimento.</p>
     */
    @NotNull(message = "El id de la ubicación no puede estar vacío")
    private Long idUbicacion;
}
