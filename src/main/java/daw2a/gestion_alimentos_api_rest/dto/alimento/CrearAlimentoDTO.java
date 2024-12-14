package daw2a.gestion_alimentos_api_rest.dto.alimento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CrearAlimentoDTO {
    @NotBlank(message = "El nombre del alimento no puede estar vacío")
    private String nombre;

    @Pattern(regexp = "PERECEDERO|NO PERECEDERO", message = "El tipo de alimento debe der 'PERECEDERO' o 'NO PERECEDERO'.")
    private String tipo;

    @NotNull(message = "La fecha de caducidad no puede estar vacia")
    private LocalDate fechaCaducidad;
}
