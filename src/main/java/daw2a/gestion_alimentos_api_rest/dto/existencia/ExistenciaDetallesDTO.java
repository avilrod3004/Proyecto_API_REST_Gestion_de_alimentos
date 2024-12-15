package daw2a.gestion_alimentos_api_rest.dto.existencia;

import lombok.Data;
import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) que representa los detalles de una existencia en el sistema.
 * <p>Este DTO contiene información detallada sobre la existencia, como el alimento asociado,
 * la ubicación donde se encuentra, la cantidad disponible y fechas relevantes (caducidad y entrada).</p>
 */
@Data
public class ExistenciaDetallesDTO {

    /**
     * Identificador único de la existencia.
     * <p>Este campo es utilizado para identificar de forma única cada existencia registrada.</p>
     */
    private Long id;

    /**
     * Identificador único del alimento asociado a la existencia.
     * <p>Este campo permite conocer qué alimento está relacionado con esta existencia.</p>
     */
    private Long idAlimento;

    /**
     * Nombre del alimento asociado a la existencia.
     * <p>Este campo proporciona el nombre del alimento relacionado con esta existencia.</p>
     */
    private String nombreAlimento;

    /**
     * Fecha de caducidad del alimento asociado a la existencia.
     * <p>Este campo muestra la fecha en que el alimento dejará de ser válido para su consumo.</p>
     */
    private LocalDate fechaCaducidad;

    /**
     * Identificador único de la ubicación donde se encuentra el alimento.
     * <p>Este campo permite conocer la ubicación exacta del alimento en el sistema.</p>
     */
    private Long idUbicacion;

    /**
     * Descripción de la ubicación donde se encuentra el alimento.
     * <p>Este campo proporciona una descripción detallada de la ubicación (por ejemplo, "balda superior de la alacena").</p>
     */
    private String descripcionUbicacion;

    /**
     * Tipo de la ubicación donde se encuentra el alimento.
     * <p>Este campo indica el tipo de almacenamiento de la ubicación (por ejemplo, "alacena", "nevera", "congelador").</p>
     */
    private String tipoUbicacion;

    /**
     * Cantidad de alimentos disponibles en la ubicación especificada.
     * <p>Este campo indica cuántos productos de este alimento están disponibles en esa ubicación.</p>
     */
    private Long cantidad;

    /**
     * Fecha en que el alimento fue colocado en la ubicación especificada.
     * <p>Este campo muestra la fecha en la que el alimento fue registrado en la ubicación indicada.</p>
     */
    private LocalDate fechaEntrada;
}
