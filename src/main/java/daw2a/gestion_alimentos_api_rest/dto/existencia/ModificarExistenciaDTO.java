package daw2a.gestion_alimentos_api_rest.dto.existencia;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ModificarExistenciaDTO {
    @NotNull
    private Long cantidad;
}
