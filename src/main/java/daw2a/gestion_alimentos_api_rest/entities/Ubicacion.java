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
 * Representa una ubicación física dentro de un electrodoméstico o área de almacenamiento.
 * <p>La ubicación puede corresponder a una sección específica como una balda, un estante,
 * o un compartimento en alacenas, neveras o congeladores.</p>
 *
 * <p>Esta entidad se utiliza para gestionar la organización y el almacenamiento
 * de productos o alimentos dentro del sistema.</p>
 */
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Ubicacion {

    /**
     * Identificador único de la ubicación.
     * <p>Este campo se genera automáticamente y sirve como clave primaria.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Descripción detallada de la ubicación.
     * <p>Proporciona una descripción específica del lugar, como "balda superior en la alacena"
     * o "cajón inferior del congelador".</p>
     */
    @NotBlank(message = "La descripción de la ubicación no puede estar vacía")
    private String descripcion;

    /**
     * Tipo de ubicación.
     * <p>Indica la categoría general del lugar donde se almacenan productos.
     * Los valores incluyen "Alacena", "Nevera" o "Congelador".</p>
     */
    @NotBlank(message = "El tipo de ubicación no puede estar vacío")
    private String tipoUbicacion;

    /**
     * Capacidad máxima de almacenamiento.
     * <p>Indica la cantidad máxima de productos que pueden ser almacenados en esta ubicación.
     * Este valor debe ser un número positivo.</p>
     */
    @Positive(message = "La capacidad debe ser un valor positivo")
    @NotBlank(message = "La cantidad no puede estar vacía")
    private Long capacidad;

    /**
     * Lista de existencias asociadas a esta ubicación.
     * <p>Representa los productos almacenados en esta ubicación. Cada existencia está asociada
     * a un alimento y contiene información sobre su cantidad y estado.</p>
     * <ul>
     *     <li><b>mappedBy:</b> Indica que esta relación está definida en el campo `ubicacion` de la entidad `Existencia`.</li>
     *     <li><b>cascade:</b> Las operaciones de persistencia o eliminación en una ubicación se propagan a sus existencias asociadas.</li>
     *     <li><b>orphanRemoval:</b> Las existencias huérfanas (sin una ubicación asociada) se eliminan automáticamente.</li>
     * </ul>
     */
    @OneToMany(mappedBy = "ubicacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Existencia> existencias = new ArrayList<>();
}
