package daw2a.gestion_alimentos_api_rest.dto.ubicacion;

import lombok.Data;

/**
 * Data Transfer Object (DTO) que representa la información de una ubicación en el sistema.
 * <p>Este DTO contiene los detalles de la ubicación, como su descripción, tipo y capacidad de almacenamiento.</p>
 */
@Data
public class UbicacionDTO {

    /**
     * Identificador único de la ubicación.
     * <p>Este campo representa el ID único asignado a la ubicación dentro del sistema.</p>
     */
    private Long id;

    /**
     * Descripción detallada de la ubicación.
     * <p>Por ejemplo: "balda superior en la alacena" o "cajón inferior en la nevera".</p>
     */
    private String descripcion;

    /**
     * Tipo de la ubicación.
     * <p>Este campo indica el tipo de la ubicación, como "alacena", "nevera", "congelador", etc.</p>
     */
    private String tipoUbicacion;

    /**
     * Capacidad máxima de almacenamiento de la ubicación.
     * <p>Indica la cantidad máxima de productos que la ubicación puede almacenar.</p>
     */
    private Long capacidad;

}
