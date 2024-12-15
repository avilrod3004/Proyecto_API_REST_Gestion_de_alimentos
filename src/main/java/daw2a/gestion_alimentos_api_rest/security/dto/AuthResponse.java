package daw2a.gestion_alimentos_api_rest.security.dto;

import lombok.Data;

/**
 * Clase DTO (Data Transfer Object) que representa la respuesta de autenticación que contiene el token JWT.
 *
 * <p>Esta clase es utilizada para enviar el token JWT al cliente después de una autenticación exitosa.</p>
 */
@Data
public class AuthResponse {

    /**
     * El token JWT generado después de una autenticación exitosa.
     */
    private String token;

    /**
     * Constructor vacío.
     * Este constructor es necesario para la deserialización automática del objeto desde JSON.
     */
    public AuthResponse() {
    }

    /**
     * Constructor que inicializa el token JWT.
     *
     * @param token El token JWT generado.
     */
    public AuthResponse(String token) {
        this.token = token;
    }

}
