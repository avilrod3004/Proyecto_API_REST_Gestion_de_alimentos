package daw2a.gestion_alimentos_api_rest.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Representa una sección de los de un electrodomestico de almacenaje en el sistema
 * <p>Esta entidad almacena información como la descripción, el tipo de ubicación y la capacidad</p>
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
     * Tipo general de la ubicación
     * <p>Alacena, nevera o congelador</p>
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
    private Long capacidad;

    /**
     * Relación uno a muchos con existecnias
     */
    @OneToMany(mappedBy = "ubicacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Existencia> existencias = new ArrayList<>();
}
