package daw2a.gestion_alimentos_api_rest.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

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
    private String nombre;

    /**
     * Perecedero o no perecedero
     */
    private String tipo;

    /**
     * Abierto o cerrado
     */
    private String estado;

    /**
     * Fecha en la que el alimento caduca
     */
    private LocalDate fechaCaducidad;
}
