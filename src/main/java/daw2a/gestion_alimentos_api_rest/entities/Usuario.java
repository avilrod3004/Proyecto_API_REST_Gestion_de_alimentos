package daw2a.gestion_alimentos_api_rest.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del usuario
     */
    @NotNull(message = "El nombre del usuario no puede ser nulo")
    @NotBlank(message = "El nombre del usuario no puede estar vacío")
    private String nombre;

    /**
     * Rol del usuario
     */
    @NotNull(message = "El rol del usuario no puede ser nulo")
    @NotBlank(message = "El rol del usuario no puede estar vacío")
    private String rol;
}
