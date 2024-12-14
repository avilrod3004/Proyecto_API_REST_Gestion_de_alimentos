package daw2a.gestion_alimentos_api_rest.dto.existencia;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MoverExistenciaDTO {
    @NotNull(message = "El id de la ubicación no puede estar vacío")
    private Long idUbicacion;
}
