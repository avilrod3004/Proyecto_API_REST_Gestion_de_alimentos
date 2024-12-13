package daw2a.gestion_alimentos_api_rest.dto.existencia;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExistenciaDTO {
    private Long id;
    private Long idAlimento;
    private String nombreAlimento;
    private Long idUbicacion;
    private String descripcionUbicacion;
    private Long cantidad;
    private LocalDateTime fechaEntrada;
}
