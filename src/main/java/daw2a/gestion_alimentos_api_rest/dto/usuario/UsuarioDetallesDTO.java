package daw2a.gestion_alimentos_api_rest.dto.usuario;

import lombok.Data;

@Data
public class UsuarioDetallesDTO {
    private Long id;
    private String nombre;
    private String email;
    private String rol;
}
