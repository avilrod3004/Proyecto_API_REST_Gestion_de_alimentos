package daw2a.gestion_alimentos_api_rest.dto.existencia;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data Transfer Object (DTO) utilizado para modificar la cantidad de una existencia en el sistema.
 * <p>Este DTO se usa cuando se actualiza la cantidad de un alimento en una ubicación específica.</p>
 */
@Data
public class ModificarExistenciaDTO {

    /**
     * Cantidad actualizada del alimento en la ubicación especificada.
     * <p>Este campo es utilizado para indicar la nueva cantidad disponible del alimento en la ubicación correspondiente.</p>
     */
    @NotNull(message = "La cantidad no puede estar vacía")
    private Long cantidad;
}
