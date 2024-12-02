package daw2a.gestion_alimentos_api_rest.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;


/**
 * Representa una entidad de ubicación en el sistema
 */
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Ubicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Descripción detallada de la ubicación (ej., "balda superior en la alacena")
     */
    @NotBlank(message = "La descripción de la ubicación no puede estar vacía")
    @NotNull(message = "La descripción de la ubicación no puede ser nula")
    private String descripcion;

    /**
     * Tipo general de la ubicación (alacena, nevera, congelador)
     */
    @NotNull(message = "El tipo de ubicación no puede ser nula")
    @NotBlank(message = "El tipo de ubicación no puede estar vacío")
    private String tipoUbicacion;

    /**
     * Capacidad máxima de almacenamiento en términos de cantidad de productos
     */
    @Positive(message = "La capacidad debe ser un valor positivo")
    @NotNull(message = "La cantidad no puede ser nula")
    @NotBlank(message = "La cantidad no puede estar vacía")
    private int capacidad;
}
