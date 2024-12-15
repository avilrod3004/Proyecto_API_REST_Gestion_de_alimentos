package daw2a.gestion_alimentos_api_rest.repositories;

import daw2a.gestion_alimentos_api_rest.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio para gestionar las operaciones CRUD relacionadas con la entidad {@link Usuario}.
 * <p>Este repositorio extiende {@link JpaRepository}, proporcionando acceso a los métodos básicos de persistencia
 * y permitiendo la definición de consultas personalizadas para buscar usuarios por correo electrónico y por nombre.</p>
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su correo electrónico.
     * <p>Este método permite recuperar un usuario específico en función de su dirección de correo electrónico.</p>
     *
     * @param email El correo electrónico del usuario que se busca.
     * @return Un objeto {@link Optional} que puede contener el usuario encontrado, o estar vacío si no se encuentra el usuario.
     */
    Optional<Usuario> findUsuarioByEmail(String email);

    /**
     * Busca usuarios cuyo nombre contenga una cadena específica, ignorando mayúsculas y minúsculas.
     * <p>Este método permite realizar búsquedas de usuarios que coincidan parcialmente con un nombre, sin tener en cuenta
     * las mayúsculas o minúsculas en la comparación.</p>
     *
     * @param nombre El nombre del usuario que se busca (puede ser una coincidencia parcial).
     * @param pageable Los parámetros de paginación.
     * @return Una página de usuarios cuyo nombre contiene la cadena especificada, ignorando mayúsculas y minúsculas.
     */
    Page<Usuario> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}
