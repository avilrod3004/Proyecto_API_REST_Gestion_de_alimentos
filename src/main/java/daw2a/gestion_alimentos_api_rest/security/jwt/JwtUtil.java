package daw2a.gestion_alimentos_api_rest.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Utilidad para manejar operaciones relacionadas con JSON Web Tokens (JWT).
 *
 * <p>Esta clase contiene métodos para generar, validar y extraer información de tokens JWT, como
 * el nombre de usuario y la fecha de expiración. Además, permite obtener el token desde las cookies
 * o los encabezados de la solicitud HTTP.</p>
 */
@Component
public class JwtUtil {

    /**
     * Clave utilizada para firmar y verificar los JWT.
     * Esta clave es generada automáticamente y debe mantenerse en secreto.
     */
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Clave generada automáticamente

    /**
     * Tiempo de expiración de un JWT, en milisegundos. En este caso, el token expira en 10 horas.
     */
    private final long jwtExpiration = 1000 * 60 * 60 * 10L; // 10 horas

    /**
     * Extrae el nombre de usuario (subject) del token JWT.
     *
     * @param token El token JWT.
     * @return El nombre de usuario (subject) del token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae la fecha de expiración del token JWT.
     *
     * @param token El token JWT.
     * @return La fecha de expiración del token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrae un claim específico del token JWT utilizando una función de resolución.
     *
     * @param token El token JWT.
     * @param claimsResolver Función para resolver el claim específico.
     * @param <T> El tipo de dato del claim.
     * @return El valor del claim especificado.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrae todos los claims del token JWT.
     *
     * @param token El token JWT.
     * @return Los claims del token.
     */
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key) // Configurar la clave de firma
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new IllegalArgumentException("Token inválido o expirado");
        }
    }

    /**
     * Verifica si el token JWT ha expirado.
     *
     * @param token El token JWT.
     * @return true si el token ha expirado, false en caso contrario.
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Genera un token JWT para el usuario proporcionado.
     *
     * @param userDetails Los detalles del usuario para los cuales se generará el token.
     * @return El token JWT generado.
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Crea un token JWT con los claims y el nombre de usuario especificados.
     *
     * @param claims Los claims del token.
     * @param subject El nombre de usuario (subject).
     * @return El token JWT creado.
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(key, SignatureAlgorithm.HS256) // Especificar clave y algoritmo
                .compact();
    }

    /**
     * Valida un token JWT, verificando que el nombre de usuario coincida y que el token no haya expirado.
     *
     * @param token El token JWT a validar.
     * @param userDetails Los detalles del usuario a comparar con el token.
     * @return true si el token es válido, false en caso contrario.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Extrae el token JWT de la solicitud HTTP, ya sea desde el encabezado Authorization o desde las cookies.
     *
     * @param request La solicitud HTTP.
     * @return El token JWT extraído, o null si no se encuentra.
     */
    public String obtenerTokenDeRequest(HttpServletRequest request) {
        // Priorizar token del encabezado Authorization
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }

        // Verificar token en cookies si no se encuentra en encabezados
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
