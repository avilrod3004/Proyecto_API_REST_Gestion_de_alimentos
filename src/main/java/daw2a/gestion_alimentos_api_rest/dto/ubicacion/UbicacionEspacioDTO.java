package daw2a.gestion_alimentos_api_rest.dto.ubicacion;

import lombok.Data;

/**
 * Data Transfer Object (DTO) que representa la información de espacio disponible y ocupado en una ubicación.
 * <p>Este DTO se utiliza para mostrar el espacio total, ocupado y disponible en una ubicación específica.</p>
 */
@Data
public class UbicacionEspacioDTO {

    /**
     * Tipo de ubicación (por ejemplo, "ALACENA", "NEVERA", "CONGELADOR").
     * <p>Este campo describe el tipo de la ubicación para la cual se reporta el espacio.</p>
     */
    private String tipoUbicacion;

    /**
     * Espacio disponible en la ubicación.
     * <p>Este campo indica la cantidad de espacio que está libre y disponible para almacenar productos.</p>
     */
    private Long espacioDisponible;

    /**
     * Espacio ocupado en la ubicación.
     * <p>Este campo indica la cantidad de espacio que ya está ocupado por productos dentro de la ubicación.</p>
     */
    private Long espacioOcupado;

    /**
     * Espacio total de la ubicación.
     * <p>Este campo representa la capacidad total de la ubicación, es decir, la cantidad máxima de espacio que puede almacenar.</p>
     */
    private Long espacioTotal;
}
