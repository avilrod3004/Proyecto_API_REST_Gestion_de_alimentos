package daw2a.gestion_alimentos_api_rest.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) para el registro de un nuevo usuario.
 *
 * <p>Esta clase encapsula los datos necesarios para registrar un nuevo usuario en el sistema,
 * incluyendo el nombre, correo electrónico y contraseña del usuario.</p>
 */
@Setter
@Getter
public class RegisterUserDTO {

    /**
     * Nombre completo del usuario.
     * Este campo no puede estar vacío.
     *
     * <p>Se valida que el nombre no esté vacío.</p>
     */
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    /**
     * Correo electrónico del usuario.
     * Este campo debe ser una dirección de correo electrónico válida.
     *
     * <p>Se valida que el correo electrónico no esté vacío y que tenga un formato válido.</p>
     */
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato de email inválido")
    private String email;

    /**
     * Contraseña del usuario.
     * Este campo no puede estar vacío y debe tener al menos 8 caracteres.
     *
     * <p>Se valida que la contraseña no esté vacía y que tenga un mínimo de 8 caracteres.</p>
     */
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    /**
     * Constructor sin parámetros.
     * Utilizado por frameworks como Spring para la creación de instancias.
     */
    public RegisterUserDTO() {
    }

    /**
     * Constructor con parámetros.
     *
     * @param nombre Nombre del usuario.
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     */
    public RegisterUserDTO(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

}
