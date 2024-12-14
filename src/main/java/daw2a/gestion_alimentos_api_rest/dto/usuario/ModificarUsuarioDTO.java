package daw2a.gestion_alimentos_api_rest.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ModificarUsuarioDTO {
    @NotBlank(message = "El nombre del usuario es obligatorio")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email
    private String email;

    @NotBlank(message = "La contaseña es obligatoria")
    private String password;

    @Pattern(regexp = "USUARIO|ADMINISTRADOR", message = "El rol debe der 'USUARIO' o 'ADMINISTRADOR'.")
    private String rol = "USUARIO";
}
