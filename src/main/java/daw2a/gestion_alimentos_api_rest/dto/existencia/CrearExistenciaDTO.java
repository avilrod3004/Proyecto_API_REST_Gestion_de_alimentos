package daw2a.gestion_alimentos_api_rest.dto.existencia;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CrearExistenciaDTO {
    @NotBlank(message = "El id del alimento no puede estar vacio")
    private Long idAlimento;

    @NotBlank(message = "El id de la ubicacion no puede estar vacio")
    private Long idUbicacion;

    @NotNull(message = "La cantidad no puede estar vacia")
    private Long cantidad;
}
