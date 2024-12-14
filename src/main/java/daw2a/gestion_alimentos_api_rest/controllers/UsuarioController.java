package daw2a.gestion_alimentos_api_rest.controllers;

import daw2a.gestion_alimentos_api_rest.dto.usuario.CrearUsuarioDTO;
import daw2a.gestion_alimentos_api_rest.dto.usuario.ModificarUsuarioDTO;
import daw2a.gestion_alimentos_api_rest.dto.usuario.UsuarioDTO;
import daw2a.gestion_alimentos_api_rest.dto.usuario.UsuarioDetallesDTO;
import daw2a.gestion_alimentos_api_rest.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Listar todos los usuarios, con soporte para paginación y filtrado opcional por nombre
     * @param nombre Nombre opcional de usuarios
     * @param pageable Configuración de poginación
     * @return Listado de usuarios
     */
    @GetMapping
    public ResponseEntity<Page<UsuarioDTO>> findAll(@RequestParam(required = false) String nombre, Pageable pageable) {
        Page<UsuarioDTO> usuarios = usuarioService.listarUsuarios(nombre, pageable);
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Obtener los detalles de un usuario específico por su id
     * @param id Identificador del usuario
     * @return Detalles del usuario
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDetallesDTO> obtenerUsuario(@PathVariable Long id) {
        UsuarioDetallesDTO usuario = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    /**
     * Obtener los detalles de un usuario específico por su email
     * @param email Correo electrónico del usuario
     * @return Detalles del usuario
     */
    @GetMapping("/email")
    public ResponseEntity<UsuarioDetallesDTO> obtenerUsuarioPorEmail(@RequestParam String email) {
        UsuarioDetallesDTO usuario = usuarioService.buscarUsuarioPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    /**
     * Crear un nuevo usuario a partir de un DTO
     * @param crearUsuarioDTO Datas del nuevo usuario
     * @return Detalles del usuario
     */
    @PostMapping
    public ResponseEntity<UsuarioDetallesDTO> registrarUsuario(@RequestBody @Valid CrearUsuarioDTO crearUsuarioDTO) {
        UsuarioDetallesDTO usuario = usuarioService.registrarUsuario(crearUsuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    /**
     * Actualizar los detalles de un usuario por su id
     * @param id Identificador del usuario
     * @param modificarUsuarioDTO Detalles actualizados del usuario
     * @return Usuario actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDetallesDTO> actualizarUsuario(@PathVariable Long id, @RequestBody @Valid ModificarUsuarioDTO modificarUsuarioDTO) {
        UsuarioDetallesDTO usuario = usuarioService.editarUsuario(id, modificarUsuarioDTO);
        return ResponseEntity.ok(usuario);
    }

    /**
     * Eliminar un usuario por su id
     * @param id Identificador del usuario
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}