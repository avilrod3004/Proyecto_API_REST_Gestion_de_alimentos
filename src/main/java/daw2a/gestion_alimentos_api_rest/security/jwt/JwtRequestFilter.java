package daw2a.gestion_alimentos_api_rest.security.jwt;

import daw2a.gestion_alimentos_api_rest.security.user.CustomUserDetailsService;
import daw2a.gestion_alimentos_api_rest.services.TokenBlacklistService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de solicitud JWT que valida y autentica las solicitudes que contienen un token JWT.
 *
 * <p>Este filtro intercepta las solicitudes entrantes, extrae el token JWT de los encabezados de autorización
 * o cookies, valida el token y, si es válido, autentica al usuario en el contexto de seguridad de Spring.</p>
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService userDetailsService;
    private final TokenBlacklistService tokenBlacklistService;
    private final JwtUtil jwtUtil;

    /**
     * Constructor para inicializar el filtro con los servicios necesarios.
     *
     * @param userDetailsService Servicio para cargar los detalles del usuario.
     * @param tokenBlacklistService Servicio para verificar si el token está en la blacklist.
     * @param jwtUtil Utilidad para manejar los tokens JWT.
     */
    public JwtRequestFilter(CustomUserDetailsService userDetailsService, TokenBlacklistService tokenBlacklistService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.tokenBlacklistService = tokenBlacklistService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Filtra las solicitudes entrantes, extrayendo y validando el token JWT.
     * Si el token es válido, autentica al usuario.
     *
     * @param request La solicitud HTTP entrante.
     * @param response La respuesta HTTP que se enviará.
     * @param chain La cadena de filtros que sigue después de este filtro.
     * @throws ServletException Si ocurre un error durante el procesamiento del filtro.
     * @throws IOException Si ocurre un error durante el procesamiento de la solicitud o la respuesta.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String requestPath = request.getServletPath();

        // Excluir las rutas públicas
        if (requestPath.equals("/register") || requestPath.equals("/auth/authenticate")) {
            chain.doFilter(request, response);
            return;
        }

        String username = null;
        String jwt = null;

        // Buscar el token JWT en el encabezado Authorization
        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);

            if (tokenBlacklistService.isTokenBlacklisted(jwt)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                limpiarCookie(response);
                return;
            }
        } else
            // Buscar el token JWT en las cookies
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if ("jwt".equals(cookie.getName())) { // Nombre de la cookie
                        jwt = cookie.getValue();
                        break;
                    }
                }
            }


        // Validar y autenticar el token si se obtuvo
        if (jwt != null) {
            username = jwtUtil.extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                var authentication = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Continuar con el filtro
        chain.doFilter(request, response);
    }

    /**
     * Elimina la cookie de token JWT de la respuesta HTTP.
     *
     * @param response La respuesta HTTP.
     */
    private void limpiarCookie(HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("jwt", null);
        jwtCookie.setPath("/");
        jwtCookie.setHttpOnly(true);
        jwtCookie.setMaxAge(0); // Caducar inmediatamente
        response.addCookie(jwtCookie);
    }
}
