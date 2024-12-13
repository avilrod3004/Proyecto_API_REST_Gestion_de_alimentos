package daw2a.gestion_alimentos_api_rest.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

/**
 * Representa una entidad de existencia en el sistema
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
     * Referenciada a Alimento.id
     */
    @ManyToOne
    @JoinColumn(name = "alimento_id")
    @JsonBackReference
    private Alimento alimento;

    /**
     * FOREIGN KEY
     * Referenciada a Ubicacion.id
     */
    @ManyToOne
    @JoinColumn(name = "ubicacion_id")
    @JsonBackReference
    private Ubicacion ubicacion;

    /**
     * Cantidad actual del alimento en la ubicación especificada
     */
    @Positive(message = "La cantidad debe ser un número positivo")
    private int cantidad;

    /**
     * Fecha en que el alimento fue colocado en esa ubicación
     */
    private LocalDate fechaEntrada;
}
