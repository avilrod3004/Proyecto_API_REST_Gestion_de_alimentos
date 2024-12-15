package daw2a.gestion_alimentos_api_rest.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * Representa un alimento registrado en el sistema.
 * <p>Esta entidad contiene información sobre el nombre del alimento, si es perecedero o no,
 * su estado (abierto o cerrado), su fecha de caducidad, y las existencias asociadas.</p>
 */
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Alimento {

    /**
     * Identificador único del alimento.
     * <p>Este campo se genera automáticamente y sirve como clave primaria.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * Nombre del alimento.
     * <p>Este campo almacena el nombre descriptivo del alimento, como "Leche", "Pan", etc.</p>
     */
    @NotBlank(message = "El nombre del alimento no puede estar vacío")
    private String nombre;

    /**
     * Indica si el alimento es perecedero o no.
     * <p>Este campo almacena el tipo de alimento, como "Perecedero" o "No perecedero".</p>
     */
    @NotBlank(message = "El tipo de alimento no puede estar vacío")
    private String tipo;

    /**
     * Estado del alimento.
     * <p>Indica si el alimento está abierto o cerrado.</p>
     */
    @NotBlank(message = "El estado del alimento no puede estar vacío")
    private String estado;

    /**
     * Fecha de caducidad del alimento.
     * <p>Este campo almacena la fecha en la que el alimento deja de ser apto para el consumo.
     * Debe ser una fecha futura.</p>
     */
    @Future(message = "La fecha de caducidad debe ser una fecha futura")
    @NotNull(message = "La fecha de caduciadad no puede ser nula")
    private LocalDate fechaCaducidad;

    /**
     * Lista de existencias asociadas a este alimento.
     * <p>Una existencia representa una cantidad específica de este alimento almacenada
     * en una ubicación específica.</p>
     * <ul>
     *     <li><b>mappedBy:</b> "alimento" indica que esta relación está mapeada en el campo `alimento` de la entidad `Existencia`.</li>
     *     <li><b>cascade:</b> Las operaciones de persistencia o eliminación en un alimento se propagan a sus existencias asociadas.</li>
     *     <li><b>orphanRemoval:</b> Las existencias huérfanas (sin un alimento asociado) se eliminan automáticamente.</li>
     * </ul>
     */
    @OneToMany(mappedBy = "alimento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Existencia> existencias = new ArrayList<>();
}
