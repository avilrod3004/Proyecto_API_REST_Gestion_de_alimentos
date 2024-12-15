package daw2a.gestion_alimentos_api_rest.security.user;

import daw2a.gestion_alimentos_api_rest.entities.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Implementación personalizada de la interfaz {@link UserDetails} para representar los detalles de un usuario
 * en el sistema de seguridad de Spring.
 *
 * <p>Esta clase proporciona la información de autenticación y autorización de un usuario,
 * incluyendo el rol del usuario y las credenciales.</p>
 */
public class CustomUserDetails implements UserDetails {

    private final Usuario usuario;

    /**
     * Constructor que inicializa el objeto {@link CustomUserDetails} con un usuario.
     *
     * @param usuario El objeto de tipo {@link Usuario} que contiene los detalles del usuario.
     */
    public CustomUserDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Devuelve los roles o autoridades del usuario. En este caso, el rol se obtiene del objeto {@link Usuario}.
     *
     * @return Una colección de {@link GrantedAuthority} que representa los roles del usuario.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()));
    }

    /**
     * Devuelve la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    /**
     * Devuelve el nombre de usuario del usuario. En este caso, se utiliza el email del usuario.
     *
     * @return El email del usuario.
     */
    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    /**
     * Indica si la cuenta del usuario ha expirado.
     * En este caso, siempre devuelve {@code true}, indicando que la cuenta no ha expirado.
     *
     * @return {@code true} si la cuenta no ha expirado, {@code false} si ha expirado.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indica si la cuenta del usuario está bloqueada.
     * En este caso, siempre devuelve {@code true}, indicando que la cuenta no está bloqueada.
     *
     * @return {@code true} si la cuenta no está bloqueada, {@code false} si está bloqueada.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indica si las credenciales del usuario han expirado.
     * En este caso, siempre devuelve {@code true}, indicando que las credenciales no han expirado.
     *
     * @return {@code true} si las credenciales no han expirado, {@code false} si han expirado.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indica si el usuario está habilitado.
     * En este caso, siempre devuelve {@code true}, indicando que el usuario está habilitado.
     *
     * @return {@code true} si el usuario está habilitado, {@code false} si no lo está.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
