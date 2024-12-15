package daw2a.gestion_alimentos_api_rest.security.user;

import daw2a.gestion_alimentos_api_rest.entities.Usuario;
import daw2a.gestion_alimentos_api_rest.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Servicio personalizado para cargar los detalles de un usuario a partir de su email.
 * Implementa la interfaz {@link UserDetailsService} de Spring Security.
 *
 * <p>Este servicio es utilizado por Spring Security para obtener los detalles del usuario
 * a partir del email proporcionado durante el proceso de autenticación.</p>
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Constructor que inicializa el servicio con el repositorio de usuarios.
     *
     * @param usuarioRepository El repositorio que proporciona acceso a los datos del usuario.
     */
    @Autowired
    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Carga los detalles de un usuario a partir de su email. Si el usuario no es encontrado, lanza una excepción.
     *
     * @param email El email del usuario que se va a cargar.
     * @return Un objeto {@link UserDetails} que contiene la información del usuario.
     * @throws UsernameNotFoundException Si no se encuentra un usuario con el email proporcionado.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findUsuarioByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));
        return new CustomUserDetails(usuario);
    }
}
