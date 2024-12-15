package daw2a.gestion_alimentos_api_rest.dto.alimento;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) para la creación de un alimento.
 * <p>Este DTO encapsula los datos necesarios para registrar un nuevo alimento en el sistema,
 * incluyendo validaciones que aseguran la integridad de los datos antes de procesarlos.</p>
 *
 * <p>Se utiliza en las solicitudes de creación de alimentos, generalmente en la capa de controladores.</p>
 */
@Data
public class CrearAlimentoDTO {

    /**
     * Nombre del alimento.
     * <p>Debe ser un texto no vacío que describa el nombre del alimento.</p>
     * <p>Ejemplo: "Leche", "Pan integral".</p>
     *
     * @implNote Este campo es obligatorio.
     */

    @NotBlank(message = "El nombre del alimento no puede estar vacío")
    private String nombre;

    /**
     * Tipo del alimento.
     * <p>Debe ser un valor predefinido que indique si el alimento es perecedero o no.</p>
     * <p>Valores permitidos: "PERECEDERO" o "NO PERECEDERO".</p>
     *
     * @implNote La validación utiliza una expresión regular para limitar las opciones.
     */
    @Pattern(regexp = "PERECEDERO|NO PERECEDERO", message = "El tipo de alimento debe der 'PERECEDERO' o 'NO PERECEDERO'.")
    private String tipo;

    /**
     * Fecha de caducidad del alimento.
     * <p>Representa la fecha límite de consumo seguro del alimento.</p>
     * <p>Debe ser una fecha futura y no puede estar vacía.</p>
     *
     * @implNote Es obligatorio proporcionar una fecha válida.
     */
    @Future(message = "La fecha de caducidad debe ser una fecha futura")
    @NotNull(message = "La fecha de caducidad no puede estar vacia")
    private LocalDate fechaCaducidad;
}
