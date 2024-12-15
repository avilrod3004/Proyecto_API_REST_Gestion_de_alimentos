package daw2a.gestion_alimentos_api_rest.dto.ubicacion;

import lombok.Data;

/**
 * Data Transfer Object (DTO) utilizado para modificar una ubicación existente en el sistema.
 * <p>Este DTO permite actualizar la descripción, el tipo y la capacidad de una ubicación ya existente.</p>
 */
@Data
public class ModificarUbicacionDTO {

    /**
     * Descripción detallada de la ubicación.
     * <p>Este campo permite actualizar la descripción de la ubicación (por ejemplo, "balda superior de la alacena").</p>
     */
    private String descripcion;

    /**
     * Tipo de la ubicación.
     * <p>Este campo permite actualizar el tipo de la ubicación (por ejemplo: "ALACENA", "NEVERA", "CONGELADOR").</p>
     */
    private String tipoUbicacion;

    /**
     * Capacidad de la ubicación.
     * <p>Este campo permite actualizar la capacidad máxima de almacenamiento de la ubicación, en términos de la cantidad máxima de productos que puede almacenar.</p>
     */
    private Long capacidad;
}
