package daw2a.gestion_alimentos_api_rest.dto.alimento;

import lombok.Data;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) para representar un alimento.
 * <p>Este DTO encapsula los datos necesarios para transferir información sobre los alimentos
 * entre las capas de la aplicación, como el controlador y el servicio.</p>
 *
 * <p>Incluye información básica sobre el alimento, como su identificador, nombre, tipo, estado
 * y fecha de caducidad.</p>
 */
@Data
public class AlimentoDTO {

    /**
     * Identificador único del alimento.
     * <p>Se utiliza para identificar de manera única un alimento en el sistema.</p>
     */
    private Long id;

    /**
     * Nombre del alimento.
     * <p>Representa el nombre común o descriptivo del alimento (ej.: "Leche", "Pan integral").</p>
     */
    private String nombre;

    /**
     * Tipo del alimento.
     * <p>Indica si el alimento es perecedero o no perecedero.</p>
     * <p>Ejemplo de valores: "Perecedero", "No perecedero".</p>
     */
    private String tipo;

    /**
     * Estado del alimento.
     * <p>Describe si el alimento está abierto o cerrado.</p>
     * <p>Ejemplo de valores: "Abierto", "Cerrado".</p>
     */
    private String estado;

    /**
     * Fecha de caducidad del alimento.
     * <p>Indica la fecha límite para el consumo seguro del alimento.</p>
     * <p>Debe ser una fecha válida en el futuro.</p>
     */
    private LocalDate fechaCaducidad;
}
