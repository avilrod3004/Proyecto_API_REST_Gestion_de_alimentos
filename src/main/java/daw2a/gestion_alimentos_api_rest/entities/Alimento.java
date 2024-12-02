package daw2a.gestion_alimentos_api_rest.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import jakarta.validation.constraints.*;

import java.beans.XMLEncoder;
import java.time.LocalDate;

/**
 * Representa una entidad de alimento en el sistema
 */
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Alimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del alimento
     */
    @NotBlank(message = "El nombre del alimento no puede estar vacío")
    @NotNull(message = "El nombre del alimento no puede ser nulo")
    private String nombre;

    /**
     * Perecedero o no perecedero
     */
    @NotBlank(message = "El tipo de alimento no puede estar vacío")
    @NotNull(message = "El tipo de alimento no puede ser nulo")
    private String tipo;

    /**
     * Abierto o cerrado
     */
    @NotBlank(message = "El estado del alimento no puede estar vacío")
    @NotNull(message = "El estado del alimento no puede ser nulo")
    private String estado;

    /**
     * Fecha en la que el alimento caduca
     */
    @Future(message = "La fecha de caducidad debe ser una fecha futura")
    @NotNull(message = "La fecha de caduciadad no puede ser nula")
    private LocalDate fechaCaducidad;
}
