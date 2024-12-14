package daw2a.gestion_alimentos_api_rest.dto.ubicacion;

import lombok.Data;

@Data
public class UbicacionEspacioDTO {
    private String tipoUbicacion;
    private Long espacioDisponible;
    private Long espacioOcupado;
    private Long espacioTotal;
}
