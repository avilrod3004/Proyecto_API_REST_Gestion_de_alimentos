package daw2a.gestion_alimentos_api_rest.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * Data Transfer Object (DTO) utilizado para modificar los datos de un usuario existente en el sistema.
 * <p>Este DTO permite actualizar la información del usuario, como su nombre, email, contraseña y rol.</p>
 */
@Data
public class ModificarUsuarioDTO {

    /**
     * Nombre del usuario.
     * <p>Este campo es opcional y permite actualizar el nombre del usuario en el sistema.</p>
     */
    private String nombre;

    /**
     * Correo electrónico del usuario.
     * <p>Este campo es opcional. Si se proporciona, se valida como un correo electrónico válido.</p>
     */
    @Email
    private String email;

    /**
     * Contraseña del usuario.
     * <p>Este campo es opcional. Si se proporciona, se actualiza la contraseña del usuario.</p>
     */
    private String password;

    /**
     * Rol del usuario.
     * <p>Este campo es opcional. Si se proporciona, define el rol del usuario. Debe ser 'USUARIO' o 'ADMINISTRADOR'.</p>
     */
    private String rol;
}
