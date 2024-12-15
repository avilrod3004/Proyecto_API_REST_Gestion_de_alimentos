package daw2a.gestion_alimentos_api_rest.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * Data Transfer Object (DTO) utilizado para crear un nuevo usuario en el sistema.
 * <p>Este DTO contiene los datos necesarios para crear un usuario, incluyendo su nombre, email, contraseña y rol.</p>
 */
@Data
public class CrearUsuarioDTO {

    /**
     * Nombre del usuario.
     * <p>Este campo es obligatorio y no puede estar vacío. Representa el nombre del usuario que se está creando.</p>
     */
    @NotBlank(message = "El nombre del usuario es obligatorio")
    private String nombre;

    /**
     * Correo electrónico del usuario.
     * <p>Este campo es obligatorio, debe ser un correo electrónico válido y se utiliza para la autenticación.</p>
     */
    @NotBlank(message = "El email es obligatorio")
    @Email
    private String email;

    /**
     * Contraseña del usuario.
     * <p>Este campo es obligatorio y representa la contraseña del usuario para acceder a su cuenta.</p>
     */
    @NotBlank(message = "La contaseña es obligatoria")
    private String password;

    /**
     * Rol del usuario.
     * <p>Este campo define el rol del usuario en el sistema. Puede ser 'USUARIO' o 'ADMINISTRADOR'. El valor por defecto es 'USUARIO'.</p>
     */
    @Pattern(regexp = "USUARIO|ADMINISTRADOR", message = "El rol debe der 'USUARIO' o 'ADMINISTRADOR'.")
    private String rol = "USUARIO";
}
