package daw2a.gestion_alimentos_api_rest.repositories;

import daw2a.gestion_alimentos_api_rest.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
