package daw2a.gestion_alimentos_api_rest.dto.existencia;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ExistenciaDTO {
    private Long id;
    private Long idAlimento;
    private String nombreAlimento;
    private Long idUbicacion;
    private String descripcionUbicacion;
    private Long cantidad;
    private LocalDate fechaEntrada;
}
