package daw2a.gestion_alimentos_api_rest.dto.ubicacion;

import lombok.Data;

@Data
public class ModificarUbicacionDTO {
    private String descripcion;
    private String tipoUbicacion;
    private Long capacidad;
}
