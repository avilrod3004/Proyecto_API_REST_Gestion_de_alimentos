package daw2a.gestion_alimentos_api_rest.dto.alimento;

import jakarta.annotation.Nullable;
import lombok.Data;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) para la modificación de un alimento.
 * <p>Este DTO encapsula los datos que pueden ser actualizados en una entidad de alimento
 * dentro del sistema. Todos los campos son opcionales, permitiendo la modificación parcial.</p>
 *
 * <p>Se utiliza en las solicitudes de actualización de alimentos, típicamente en la capa de controladores.</p>
 */

@Data
public class ModificarAlimentoDTO {

    /**
     * Nombre del alimento.
     * <p>Representa el nuevo nombre del alimento, si se desea actualizar.</p>
     * <p>Ejemplo: "Leche Descremada", "Pan Multicereal".</p>
     *
     * @implNote Este campo es opcional.
     */
    private String nombre;

    /**
     * Tipo del alimento.
     * <p>Define si el alimento es perecedero o no. Este campo debe contener uno de los valores
     * predefinidos en el sistema, como "PERECEDERO" o "NO PERECEDERO".</p>
     *
     * @implNote Este campo es opcional y puede estar vacío si no se desea modificar.
     */
    private String tipo;

    /**
     * Estado del alimento.
     * <p>Indica si el alimento está "ABIERTO" o "CERRADO".</p>
     * <p>Este campo permite registrar cambios en el estado físico del alimento.</p>
     *
     * @implNote Este campo es opcional y solo debe actualizarse si es necesario.
     */
    private String estado;

    /**
     * Fecha de caducidad del alimento.
     * <p>Representa la nueva fecha de caducidad del alimento, si aplica. Debe ser una fecha válida.</p>
     * <p>Ejemplo: 2024-12-31.</p>
     *
     * @implNote Este campo es opcional. Si no se incluye, la fecha de caducidad no será modificada.
     */
    private LocalDate fechaCaducidad;
}
