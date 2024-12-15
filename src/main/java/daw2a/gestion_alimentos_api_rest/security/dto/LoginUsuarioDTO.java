package daw2a.gestion_alimentos_api_rest.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) para el inicio de sesión de un usuario.
 *
 * <p>Esta clase encapsula los datos de inicio de sesión necesarios para autenticar a un usuario,
 * incluyendo el correo electrónico y la contraseña.</p>
 */
@Setter
@Getter
@NoArgsConstructor @AllArgsConstructor
public class LoginUsuarioDTO {

    /**
     * Correo electrónico del usuario.
     * Este campo debe ser una dirección de correo electrónico válida.
     *
     * <p>Se valida que no esté vacío y que tenga un formato de correo electrónico correcto.</p>
     */
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato de email inválido")
    private String email;

    /**
     * Contraseña del usuario.
     * Este campo no puede estar vacío.
     *
     * <p>Se valida que la contraseña no esté vacía.</p>
     */
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
