package daw2a.gestion_alimentos_api_rest.dto.existencia;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ExistenciaDetallesDTO {
    private Long id;
    private Long idAlimento;
    private String nombreAlimento;
    private LocalDate fechaCaducidad;
    private Long idUbicacion;
    private String descripcionUbicacion;
    private String tipoUbicacion;
    private Long cantidad;
    private LocalDate fechaEntrada;
}
