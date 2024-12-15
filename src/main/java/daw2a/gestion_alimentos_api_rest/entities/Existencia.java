package daw2a.gestion_alimentos_api_rest.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

/**
 * Representa una existencia de un alimento en una ubicación específica del sistema.
 * <p>Esta entidad almacena información sobre la cantidad de un alimento
 * que se encuentra en una ubicación específica, junto con la fecha en la que
 * fue colocada en dicha ubicación.</p>
 *
 * <p>Está asociada a las entidades {@link Alimento} y {@link Ubicacion}.</p>
 */
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Existencia {

    /**
     * Identificador único de la existencia.
     * <p>Este campo se genera automáticamente y sirve como clave primaria.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Relación muchos-a-uno con {@link Alimento}.
     * <p>Una existencia está asociada a un único alimento, pero un alimento puede
     * tener múltiples existencias en diferentes ubicaciones.</p>
     *
     * <ul>
     *   <li><b>JoinColumn:</b> Define la clave foránea `alimento_id` en esta tabla.</li>
     *   <li><b>JsonBackReference:</b> Evita bucles en la serialización JSON.</li>
     * </ul>
     */
    @ManyToOne
    @JoinColumn(name = "alimento_id")
    @JsonBackReference
    private Alimento alimento;

    /**
     * Relación muchos-a-uno con {@link Ubicacion}.
     * <p>Una existencia está asociada a una única ubicación, pero una ubicación puede
     * tener múltiples existencias que representen diferentes alimentos almacenados.</p>
     *
     * <ul>
     *   <li><b>JoinColumn:</b> Define la clave foránea `ubicacion_id` en esta tabla.</li>
     *   <li><b>JsonBackReference:</b> Evita bucles en la serialización JSON.</li>
     * </ul>
     */
    @ManyToOne
    @JoinColumn(name = "ubicacion_id")
    @JsonBackReference
    private Ubicacion ubicacion;

    /**
     * Cantidad actual del alimento en la ubicación especificada.
     * <p>Este valor debe ser un número positivo. Ejemplo: 5 (cinco unidades).</p>
     */
    @Positive(message = "La cantidad debe ser un número positivo")
    @NotNull(message = "La cantidad no puede ser nula")
    private Long cantidad;

    /**
     * Fecha en que el alimento fue colocado en la ubicación.
     * <p>Indica cuándo se registró esta existencia en el sistema.</p>
     */
    @NotNull(message = "La fecha de entrada no puede ser nula")
    private LocalDate fechaEntrada;
}
