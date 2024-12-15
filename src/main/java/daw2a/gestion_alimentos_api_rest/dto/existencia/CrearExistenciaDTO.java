package daw2a.gestion_alimentos_api_rest.dto.existencia;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data Transfer Object (DTO) para crear una nueva existencia en el sistema.
 * <p>Este DTO encapsula los datos necesarios para registrar una existencia,
 * incluyendo el id del alimento, el id de la ubicación y la cantidad.</p>
 */
@Data
public class CrearExistenciaDTO {

    /**
     * Identificador único del alimento asociado a la existencia.
     * <p>Este campo se utiliza para asociar la existencia con un alimento
     * ya existente en el sistema.</p>
     */
    @NotBlank(message = "El id del alimento no puede estar vacio")
    private Long idAlimento;

    /**
     * Identificador único de la ubicación asociada a la existencia.
     * <p>Este campo se utiliza para asociar la existencia con una ubicación
     * específica donde se almacenará el alimento.</p>
     */
    @NotBlank(message = "El id de la ubicacion no puede estar vacio")
    private Long idUbicacion;

    /**
     * Cantidad de alimentos disponibles en la ubicación especificada.
     * <p>Este campo no puede estar vacío y debe ser un número positivo.</p>
     */
    @NotNull(message = "La cantidad no puede estar vacia")
    private Long cantidad;
}
