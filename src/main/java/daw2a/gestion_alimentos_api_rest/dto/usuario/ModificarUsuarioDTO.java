package daw2a.gestion_alimentos_api_rest.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ModificarUsuarioDTO {
    private String nombre;

    @Email
    private String email;

    private String password;

    private String rol;
}
