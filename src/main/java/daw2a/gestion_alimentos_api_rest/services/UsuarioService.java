package daw2a.gestion_alimentos_api_rest.services;

import daw2a.gestion_alimentos_api_rest.enums.Rol;
import daw2a.gestion_alimentos_api_rest.dto.usuario.CrearUsuarioDTO;
import daw2a.gestion_alimentos_api_rest.dto.usuario.ModificarUsuarioDTO;
import daw2a.gestion_alimentos_api_rest.dto.usuario.UsuarioDTO;
import daw2a.gestion_alimentos_api_rest.dto.usuario.UsuarioDetallesDTO;
import daw2a.gestion_alimentos_api_rest.entities.Usuario;
import daw2a.gestion_alimentos_api_rest.exceptions.RecursoDuplicadoException;
import daw2a.gestion_alimentos_api_rest.exceptions.RecursoNoEncontradoException;
import daw2a.gestion_alimentos_api_rest.repositories.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servicio para gestionar operaciones relacionadas con los usuarios
 * Proporciona funcionalidades de creación, actualización, eliminación y consulta de usuarios
 */
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Contructor para inyección de dependencias
     * @param usuarioRepository Repositorio de usuarios
     * @param passwordEncoder Codificador de contraseñas
     */
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Listar usuarios con soporte de paginación y filtrado opcional por nombre
     * @param nombre Filtrado opcional por nombre del usuario
     * @param pageable Configuración de paginación
     * @return Página de usuarios en formato DTO de listado
     */
    public Page<UsuarioDTO> listarUsuarios(String nombre, Pageable pageable) {
        Page<Usuario> usuarios;

        if (nombre != null && !nombre.isEmpty()) {
            usuarios = usuarioRepository.findByNombreContainingIgnoreCase(nombre, pageable);
        } else {
            usuarios = usuarioRepository.findAll(pageable);
        }

        return usuarios.map(this::convertirAUsuarioDTO);
    }

    /**
     * Obtiene los detalles de un usuario por su id
     * @param id Identificador del usuario
     * @return Detalles del usuario en formato DTO
     * @throws RecursoNoEncontradoException Si no se encuentra el usuario
     */
    public UsuarioDetallesDTO buscarUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El usuario con el id " + id + " no existe."));

        return convertirAUsuarioDetallesDTO(usuario);
    }

    /**
     * Obtiene los detalles de un usuario por su correo electronico
     * @param email Correo electronico del usuario
     * @return Detalles del usuario en formato DTO
     * @throws RecursoNoEncontradoException Si no se encuentra el usuario
     */
    public UsuarioDetallesDTO buscarUsuarioPorEmail(String email) {
        Usuario usuario = usuarioRepository.findUsuarioByEmail(email)
                .orElseThrow(() -> new RecursoNoEncontradoException("El usuario con el email " + email + " no existe."));

        return convertirAUsuarioDetallesDTO(usuario);
    }

    /**
     * Crear un usuario nuevo en el sistema
     * @param crearUsuarioDTO Datos para crear el usuario
     * @return Detalles del usuario creado
     * @throws RecursoDuplicadoException Si el email ya está en uso
     */
    public UsuarioDetallesDTO crearUsuario(CrearUsuarioDTO crearUsuarioDTO) {
        if (usuarioRepository.findUsuarioByEmail(crearUsuarioDTO.getEmail()).isPresent()) {
            throw new RecursoDuplicadoException("El email " + crearUsuarioDTO.getEmail() + " ya está en uso.");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(crearUsuarioDTO.getNombre());
        usuario.setEmail(crearUsuarioDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(crearUsuarioDTO.getPassword()));
        usuario.setRol(Rol.valueOf(crearUsuarioDTO.getRol().toUpperCase()));

        return convertirAUsuarioDetallesDTO(usuarioRepository.save(usuario));
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param crearUsuarioDTO Datos para registrar un nuevo usuario
     * @return Entidad de usuario registrada
     * @throws RecursoDuplicadoException Si el email ya está en uso
     */
    public Usuario registrarUsuario(CrearUsuarioDTO crearUsuarioDTO) {
        if (usuarioRepository.findUsuarioByEmail(crearUsuarioDTO.getEmail()).isPresent()) {
            throw new RecursoDuplicadoException("El email " + crearUsuarioDTO.getEmail() + " ya está en uso.");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(crearUsuarioDTO.getNombre());
        usuario.setEmail(crearUsuarioDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(crearUsuarioDTO.getPassword()));
        usuario.setRol(Rol.valueOf(crearUsuarioDTO.getRol().toUpperCase()));

        // Guardar y devolver el usuario
        return usuarioRepository.save(usuario);
    }

    /**
     * Actualizar un usuario existente
     * @param id Identificador del usuario
     * @param modificarUsuarioDTO Detalles para modificar el usuario
     * @return Detalles del usuario actualizado
     * @throws RecursoNoEncontradoException Si el usuario no existe
     */
    public UsuarioDetallesDTO editarUsuario(Long id, ModificarUsuarioDTO modificarUsuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El usuario con el id " + id + " no existe."));

        if (modificarUsuarioDTO.getNombre() != null && !modificarUsuarioDTO.getNombre().isEmpty()) {
            usuario.setNombre(modificarUsuarioDTO.getNombre());
        }
        if (modificarUsuarioDTO.getEmail() != null && !modificarUsuarioDTO.getEmail().isEmpty()) {
            usuario.setEmail(modificarUsuarioDTO.getEmail());
        }
        if (modificarUsuarioDTO.getPassword() != null && !modificarUsuarioDTO.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(modificarUsuarioDTO.getPassword()));
        }
        if (modificarUsuarioDTO.getRol() != null && !modificarUsuarioDTO.getRol().isEmpty()) {
            usuario.setRol(Rol.valueOf(modificarUsuarioDTO.getRol().toUpperCase()));
        }

        return convertirAUsuarioDetallesDTO(usuarioRepository.save(usuario));
    }

    /**
     * Elimina un usuario existente
     * @param id Identificador del usuario
     * @throws RecursoNoEncontradoException Si el usuario no existe
     */
    public void eliminarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El usuario con el id " + id + " no existe."));

        usuarioRepository.delete(usuario);
    }

    /**
     * Convierte una entidad usuario a un DTO con los datos basicos
     * @param usuario Entidad de usuario
     * @return DTO con detalles del usuario
     */
    private UsuarioDTO convertirAUsuarioDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setEmail(usuario.getEmail());

        return usuarioDTO;
    }

    /**
     * Convierte una entidad usuario a un DTO con detalles
     * @param usuario Entidad de usuario
     * @return DTO con detalles del usuario
     */
    private UsuarioDetallesDTO convertirAUsuarioDetallesDTO(Usuario usuario) {
        UsuarioDetallesDTO usuarioDetallesDTO = new UsuarioDetallesDTO();
        usuarioDetallesDTO.setId(usuario.getId());
        usuarioDetallesDTO.setNombre(usuario.getNombre());
        usuarioDetallesDTO.setEmail(usuario.getEmail());
        usuarioDetallesDTO.setRol(usuario.getRol().name());

        return usuarioDetallesDTO;
    }
}
