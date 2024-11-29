package daw2a.gestion_alimentos_api_rest.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String descripcion;

    /**
     * Tipo general de la ubicación (alacena, nevera, congelador)
     */
    private String tipoUbicacion;

    /**
     * Capacidad máxima de almacenamiento en términos de cantidad de productos
     */
    private int capacidad;
}
