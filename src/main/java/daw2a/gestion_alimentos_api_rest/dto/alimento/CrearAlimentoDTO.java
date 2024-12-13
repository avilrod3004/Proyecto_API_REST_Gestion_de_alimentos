package daw2a.gestion_alimentos_api_rest.dto.alimento;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CrearAlimentoDTO {
    private String nombre;
    private String tipo;
    private LocalDate fechaCaducidad;
}
