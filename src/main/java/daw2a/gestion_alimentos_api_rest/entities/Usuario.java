package daw2a.gestion_alimentos_api_rest.entities;

import daw2a.gestion_alimentos_api_rest.enums.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Representa un usuario en el sistema.
 * <p>Esta entidad almacena información sobre el usuario como su nombre, email, contraseña y rol</p>
 */
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Usuario {

    /**
     * Identifica al usuario de forma única
     * <p>Este campo se genera automáticamente en la base de datos</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del usuario
     * <p>No debe ser nulo ni estar vacío</p>
     */
    @NotNull(message = "El nombre del usuario no puede ser nulo")
    @NotBlank(message = "El nombre del usuario no puede estar vacío")
    private String nombre;

    /**
     * Email del usuario
     * <p>Usando para autenticación</p>
     */
    @Email
    @Column(unique = true)
    private String email;

    /**
     * Contraseña de la cuenta del usuario
     */
    @NotNull(message = "La contraseña del usuario no puede ser nula")
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

    /**
     * Rol del usuario
     * <p>Define los permisos del usuario</p>
     */
    @NotNull(message = "El rol del usuario no puede ser nulo")
    @NotBlank(message = "El rol del usuario no puede estar vacío")
    private Rol rol;
}
