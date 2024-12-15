package daw2a.gestion_alimentos_api_rest.security.controller;

import daw2a.gestion_alimentos_api_rest.dto.usuario.CrearUsuarioDTO;
import daw2a.gestion_alimentos_api_rest.entities.Usuario;
import daw2a.gestion_alimentos_api_rest.security.dto.AuthResponse;
import daw2a.gestion_alimentos_api_rest.security.dto.LoginUsuarioDTO;
import daw2a.gestion_alimentos_api_rest.security.jwt.JwtUtil;
import daw2a.gestion_alimentos_api_rest.security.user.CustomUserDetails;
import daw2a.gestion_alimentos_api_rest.services.TokenBlacklistService;
import daw2a.gestion_alimentos_api_rest.services.UsuarioService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador encargado de gestionar la autenticación, el registro de usuarios y el cierre de sesión.
 * Utiliza JWT para la autenticación y maneja las cookies de sesión de los usuarios.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;
    private final TokenBlacklistService tokenBlacklistService;

    /**
     * Constructor que inyecta las dependencias necesarias para la autenticación y el registro de usuarios.
     *
     * @param authenticationManager El administrador de autenticación de Spring Security.
     * @param jwtUtil Utilidad para generar y validar tokens JWT.
     * @param usuarioService Servicio para gestionar los usuarios.
     * @param tokenBlacklistService Servicio para gestionar la lista negra de tokens.
     */
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UsuarioService usuarioService, TokenBlacklistService tokenBlacklistService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    /**
     * Endpoint para autenticar a un usuario y generar un token JWT.
     * <p>El token se devuelve en la respuesta y se guarda en una cookie HTTP para el cliente.</p>
     *
     * @param request Datos de inicio de sesión del usuario (email y contraseña).
     * @param response La respuesta HTTP donde se añadirá la cookie con el token JWT.
     * @return Un objeto {@link AuthResponse} que contiene el token JWT generado.
     */
    @PostMapping("/authenticate")
    public AuthResponse authenticate(@RequestBody LoginUsuarioDTO request, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        String token = jwtUtil.generateToken((CustomUserDetails) authentication.getPrincipal());

        //Crear la cookie
        Cookie jwtCookie = new Cookie("jwt", token);
        jwtCookie.setHttpOnly(true); // Solo accesible desde el servidor
        jwtCookie.setSecure(true); // Solo en conexiones HTTPS
        jwtCookie.setPath("/"); // Disponible para toda la aplicación
        jwtCookie.setMaxAge(60 * 60 * 10); // Validez de 10 horas

        // Agregar la cookie a la respuesta
        response.addCookie(jwtCookie);
        return new AuthResponse(token);
    }

    /**
     * Endpoint para cerrar sesión de un usuario.
     * <p>El token JWT es añadido a la lista negra y la cookie con el token se elimina.</p>
     *
     * @param request La solicitud HTTP que puede contener un token en el encabezado de autorización o en la cookie.
     * @param response La respuesta HTTP donde se eliminará la cookie con el token.
     * @return Una respuesta indicando si el cierre de sesión fue exitoso o si no se encontró un token válido.
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        // Manejar el token desde el encabezado Authorization
        String authorizationHeader = request.getHeader("Authorization");
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
        }

        // Manejar el token desde la cookie
        if (jwt == null && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    jwt = cookie.getValue();
                }
            }
        }

        // Si se encuentra el token, añadirlo a la blacklist
        if (jwt != null) {
            tokenBlacklistService.addTokenToBlacklist(jwt);

            // Eliminar la cookie
            Cookie jwtCookie = new Cookie("jwt", null);
            jwtCookie.setPath("/");
            jwtCookie.setHttpOnly(true);
            jwtCookie.setMaxAge(0); // Caducar inmediatamente
            response.addCookie(jwtCookie);

            return ResponseEntity.ok("Logout exitoso. Token añadido a la blacklist y cookie eliminada.");
        }

        return ResponseEntity.badRequest().body("No se encontró un token válido para cerrar sesión.");
    }

    /**
     * Endpoint para registrar un nuevo usuario.
     *
     * <p>El usuario es creado en la base de datos y se genera un token JWT para su autenticación.</p>
     *
     * @param crearUsuarioDTO Los datos necesarios para crear un nuevo usuario (nombre, email, contraseña, etc.).
     * @return Un objeto {@link AuthResponse} que contiene el token JWT generado para el nuevo usuario.
     */
    @PostMapping("/register")
    public AuthResponse register(@RequestBody @Valid CrearUsuarioDTO crearUsuarioDTO) {
        // Registrar usuario usando el servicio
        Usuario nuevoUsuario = usuarioService.registrarUsuario(crearUsuarioDTO);

        // Generar token JWT para el nuevo usuario
        String token = jwtUtil.generateToken(new CustomUserDetails(nuevoUsuario));

        // Devolver el token en la respuesta
        return new AuthResponse(token);
    }
}
