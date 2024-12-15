package daw2a.gestion_alimentos_api_rest.dto.ubicacion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * Data Transfer Object (DTO) utilizado para crear una nueva ubicación en el sistema.
 * <p>Este DTO contiene la información necesaria para definir una nueva ubicación, incluyendo la descripción, tipo y capacidad.</p>
 */
@Data
public class CrearUbicacionDTO {

    /**
     * Descripción detallada de la ubicación.
     * <p>Debe proporcionar información clara sobre la ubicación, por ejemplo: "balda superior de la alacena".</p>
     */
    @NotBlank(message = "El id del alimento no puede estar vacio")
    private String descripcion;

    /**
     * Tipo de la ubicación.
     * <p>El tipo de la ubicación debe ser uno de los siguientes: "ALACENA", "NEVERA" o "CONGELADOR".</p>
     */
    @Pattern(regexp = "ALACENA|NEVERA|CONGELADOR", message = "El tipo de ubicación debe der 'ALACENA', 'CONGELADOR'  o 'NEVERA'.")
    private String tipoUbicacion;

    /**
     * Capacidad de la ubicación en términos de cantidad máxima de productos que puede almacenar.
     * <p>Este campo debe contener un valor numérico positivo que indique la cantidad máxima de productos.</p>
     */
    @NotNull(message = "La capacidad no puede estar vacia")
    private Long capacidad;
}
