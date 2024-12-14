package daw2a.gestion_alimentos_api_rest.dto.ubicacion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CrearUbicacionDTO {
    @NotBlank(message = "El id del alimento no puede estar vacio")
    private String descripcion;

    @Pattern(regexp = "ALACENA|NEVERA|CONGELADOR", message = "El tipo de ubicación debe der 'ALACENA', 'CONGELADOR'  o 'NEVERA'.")
    private String tipoUbicacion;

    @NotNull(message = "La capacidad no puede estar vacia")
    private Long capacidad;
}
