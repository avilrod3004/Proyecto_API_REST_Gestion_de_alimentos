package daw2a.gestion_alimentos_api_rest.dto.existencia;

import lombok.Data;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) para representar una existencia en el sistema.
 * <p>Este DTO encapsula los datos de una existencia, incluyendo su relación
 * con un alimento y una ubicación, así como información sobre la cantidad
 * y la fecha de entrada.</p>
 */
@Data
public class ExistenciaDTO {

    /**
     * Identificador único de la existencia.
     * <p>Generado automáticamente por el sistema.</p>
     */
    private Long id;

    /**
     * Identificador único del alimento asociado a la existencia.
     * <p>Representa la relación entre la existencia y el alimento.</p>
     */
    private Long idAlimento;

    /**
     * Nombre del alimento asociado a la existencia.
     * <p>Proporciona una descripción legible del alimento.</p>
     */
    private String nombreAlimento;

    /**
     * Identificador único de la ubicación asociada a la existencia.
     * <p>Representa la relación entre la existencia y la ubicación.</p>
     */
    private Long idUbicacion;

    /**
     * Descripción detallada de la ubicación asociada a la existencia.
     * <p>Por ejemplo: "balda superior en la alacena".</p>
     */
    private String descripcionUbicacion;

    /**
     * Cantidad actual del alimento en la ubicación especificada.
     * <p>Debe ser un valor positivo.</p>
     */
    private Long cantidad;

    /**
     * Fecha en la que el alimento fue colocado en la ubicación.
     * <p>Útil para realizar un seguimiento del tiempo de almacenamiento.</p>
     */
    private LocalDate fechaEntrada;
}
