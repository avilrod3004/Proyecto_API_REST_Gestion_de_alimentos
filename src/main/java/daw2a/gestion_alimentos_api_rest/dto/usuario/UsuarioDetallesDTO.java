package daw2a.gestion_alimentos_api_rest.dto.usuario;

import lombok.Data;

/**
 * Data Transfer Object (DTO) utilizado para representar los detalles de un usuario en el sistema.
 * <p>Este DTO contiene información básica del usuario, como su ID, nombre, correo electrónico y rol.</p>
 */
@Data
public class UsuarioDetallesDTO {

    /**
     * Identificador único del usuario.
     * <p>Este campo almacena el ID único del usuario en el sistema.</p>
     */
    private Long id;

    /**
     * Nombre del usuario.
     * <p>Este campo almacena el nombre completo del usuario.</p>
     */
    private String nombre;

    /**
     * Correo electrónico del usuario.
     * <p>Este campo almacena la dirección de correo electrónico del usuario.</p>
     */
    private String email;

    /**
     * Rol del usuario en el sistema.
     * <p>Este campo indica el rol del usuario, como 'USUARIO' o 'ADMINISTRADOR'.</p>
     */
    private String rol;
}
