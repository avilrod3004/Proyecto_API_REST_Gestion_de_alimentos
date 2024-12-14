package daw2a.gestion_alimentos_api_rest.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

/**
 * Representa un alimento y su ubicación en el sistema
 * <p>Almacena información como el id del alimento, el id de su ubicación, la cantidad y la fecha de entrada</p>
 */
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Existencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * FOREIGN KEY
     * Id del alimento
     */
    @ManyToOne
    @JoinColumn(name = "alimento_id")
    @JsonBackReference
    private Alimento alimento;

    /**
     * FOREIGN KEY
     * Id de la ubicación
     */
    @ManyToOne
    @JoinColumn(name = "ubicacion_id")
    @JsonBackReference
    private Ubicacion ubicacion;

    /**
     * Cantidad actual del alimento en la ubicación especificada
     */
    @Positive(message = "La cantidad debe ser un número positivo")
    @NotNull(message = "La cantidad no puede ser nula")
    private Long cantidad;

    /**
     * Fecha en que el alimento fue colocado en esa ubicación
     */
    private LocalDate fechaEntrada;
}
