package daw2a.gestion_alimentos_api_rest.entities;

import daw2a.gestion_alimentos_api_rest.enums.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Representa un usuario en el sistema.
 * <p>Un usuario tiene un nombre, correo electrónico, contraseña y un rol que
 * define sus permisos dentro del sistema. Esta entidad es utilizada para la
 * autenticación y autorización en la aplicación.</p>
 *
 * <p>Los usuarios pueden tener diferentes roles, definidos por la enumeración {@link Rol}.</p>
 */
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Usuario {

    /**
     * Identificador único del usuario.
     * <p>Este campo se genera automáticamente en la base de datos y
     * actúa como clave primaria para la entidad.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del usuario.
     * <p>Este campo es obligatorio y debe contener el nombre del usuario.
     * Ejemplo: "Juan Pérez".</p>
     */
    @NotBlank(message = "El nombre del usuario no puede estar vacío")
    private String nombre;

    /**
     * Dirección de correo electrónico del usuario.
     * <p>Este campo es obligatorio y debe ser único en el sistema.
     * Es utilizado para la autenticación del usuario.</p>
     * <p><b>Restricciones:</b></p>
     * <ul>
     *   <li>Debe ser una dirección de correo válida.</li>
     *   <li>No puede estar vacío.</li>
     *   <li>Debe ser único en la base de datos.</li>
     * </ul>
     */
    @Email
    @NotBlank(message = "El email no puede estar vacío")
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Contraseña del usuario.
     * <p>Este campo es obligatorio y debe contener la contraseña encriptada
     * del usuario. Nunca se almacena en texto plano.</p>
     */
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

    /**
     * Rol del usuario en el sistema.
     * <p>Este campo define los permisos asociados al usuario y es obligatorio.
     * El valor debe coincidir con uno de los valores definidos en la enumeración {@link Rol}.</p>
     *
     * <p><b>Ejemplo:</b> ADMINISTRADOR, USUARIO.</p>
     */
    @NotNull(message = "El rol del usuario no puede ser nulo")
    @Enumerated(EnumType.STRING)
    private Rol rol;
}
