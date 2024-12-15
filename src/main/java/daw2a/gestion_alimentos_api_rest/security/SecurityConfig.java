package daw2a.gestion_alimentos_api_rest.security;

import daw2a.gestion_alimentos_api_rest.security.jwt.JwtRequestFilter;
import daw2a.gestion_alimentos_api_rest.security.user.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración de seguridad de la aplicación.
 * Esta clase define la configuración de seguridad utilizando Spring Security, incluyendo la protección de endpoints,
 * el uso de JWT para autenticación y la configuración de roles y permisos.
 *
 * <p>Se habilita la autenticación básica, se configura la política de sesiones para ser stateless,
 * y se establece un filtro JWT para manejar las solicitudes de autenticación.</p>
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Constructor que inyecta las dependencias necesarias para la configuración de seguridad.
     *
     * @param jwtRequestFilter Filtro para manejar las solicitudes de JWT.
     * @param customUserDetailsService Servicio para cargar detalles de usuario personalizados.
     */
    public SecurityConfig(JwtRequestFilter jwtRequestFilter, CustomUserDetailsService customUserDetailsService) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Bean para definir el encoder de contraseñas.
     * <p>Utiliza el algoritmo BCrypt para encriptar las contraseñas de los usuarios.</p>
     *
     * @return Un objeto {@link PasswordEncoder} que implementa el algoritmo BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean para crear el {@link AuthenticationManager} a partir de la configuración de autenticación.
     *
     * @param authConfig Configuración de autenticación que permite la creación del {@link AuthenticationManager}.
     * @return El {@link AuthenticationManager} configurado.
     * @throws Exception Si ocurre un error al obtener el AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Bean para configurar la cadena de seguridad, que define las reglas de acceso a los diferentes endpoints
     * y la política de gestión de sesiones.
     *
     * @param http Objeto {@link HttpSecurity} utilizado para configurar la seguridad HTTP.
     * @return La configuración de seguridad con las reglas definidas.
     * @throws Exception Si ocurre un error durante la configuración de la seguridad HTTP.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Deshabilitar CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/authenticate", "/auth/register").permitAll()
                        .requestMatchers("/alimentos/**").hasAnyRole("USUARIO","ADMINISTRADOR")
                        .requestMatchers("/existencias/**").hasAnyRole("ADMINISTRADOR", "USUARIO")
                        .requestMatchers(("/ubicaciones/**")).hasAnyRole("ADMINISTRADOR","USUARIO")
                        .requestMatchers("/usuarios/vista").hasRole("ADMINISTRADOR")
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated() // Requiere autenticación para otras rutas
                )
                .httpBasic(Customizer.withDefaults()) // Habilitar Basic Authentication
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless para JWT
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Agregar filtro JWT

        return http.build();
    }
}

