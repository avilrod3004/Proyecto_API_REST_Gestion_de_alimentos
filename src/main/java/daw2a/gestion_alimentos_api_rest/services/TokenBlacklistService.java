package daw2a.gestion_alimentos_api_rest.services;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Servicio encargado de gestionar los tokens en la lista negra.
 * Un token en la lista negra es un token que ha sido invalidado y no se debe permitir su uso.
 */
@Service
public class TokenBlacklistService {
    // Conjunto que contiene los tokens que han sido puestos en la lista negra
    private final Set<String> blacklistedTokens = new HashSet<>();

    /**
     * Añade un token a la lista negra para invalidarlo.
     * Este token no podrá ser utilizado en futuras solicitudes de autenticación.
     *
     * @param token El token que se desea añadir a la lista negra.
     */
    public void addTokenToBlacklist(String token) {
        blacklistedTokens.add(token);
    }

    /**
     * Verifica si un token se encuentra en la lista negra.
     * Un token en la lista negra no podrá ser utilizado para autenticarse.
     *
     * @param token El token a verificar.
     * @return {@code true} si el token está en la lista negra, {@code false} en caso contrario.
     */
    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }
}
