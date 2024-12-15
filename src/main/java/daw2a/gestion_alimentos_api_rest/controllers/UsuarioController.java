package daw2a.gestion_alimentos_api_rest.controllers;

import daw2a.gestion_alimentos_api_rest.dto.usuario.CrearUsuarioDTO;
import daw2a.gestion_alimentos_api_rest.dto.usuario.ModificarUsuarioDTO;
import daw2a.gestion_alimentos_api_rest.dto.usuario.UsuarioDTO;
import daw2a.gestion_alimentos_api_rest.dto.usuario.UsuarioDetallesDTO;
import daw2a.gestion_alimentos_api_rest.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gestionar los usuarios en el sistema.
 * Este controlador expone los endpoints para crear, modificar, listar y obtener detalles de los usuarios.
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    /**
     * Constructor para inyectar el servicio de usuarios.
     * @param usuarioService Servicio para gestionar operaciones de usuario.
     */
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Endpoint para obtener una lista de usuarios con soporte de paginación y filtrado opcional por nombre.
     *
     * @param nombre Filtrado opcional por el nombre del usuario.
     * @param pageable Configuración de paginación.
     * @return Página de usuarios en formato DTO.
     */
    @Operation(summary = "Obtener lista de usuarios",
            description = "Recupera una lista de usuarios con soporte para filtrado por nombre y paginación.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Petición incorrecta")
            })
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Page<UsuarioDTO>> listarUsuarios(@RequestParam(required = false) String nombre, Pageable pageable) {
        Page<UsuarioDTO> usuarios = usuarioService.listarUsuarios(nombre, pageable);
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Endpoint para obtener los detalles de un usuario por su identificador.
     *
     * @param id Identificador del usuario.
     * @return Detalles del usuario en formato DTO.
     */
    @Operation(summary = "Obtener detalles de un usuario",
            description = "Recupera los detalles de un usuario específico utilizando su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Detalles del usuario obtenidos exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<UsuarioDetallesDTO> obtenerUsuario(@PathVariable Long id) {
        UsuarioDetallesDTO usuario = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    /**
     * Obtener los detalles de un usuario específico por su email.
     *
     * @param email Correo electrónico del usuario.
     * @return Detalles del usuario en formato DTO.
     */
    @Operation(summary = "Obtener detalles de un usuario por email",
            description = "Recupera los detalles de un usuario utilizando su email.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Detalles del usuario obtenidos exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
            })
    @GetMapping("/email")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<UsuarioDetallesDTO> obtenerUsuarioPorEmail(@RequestParam String email) {
        UsuarioDetallesDTO usuario = usuarioService.buscarUsuarioPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    /**
     * Endpoint para crear un nuevo usuario a partir de un DTO.
     *
     * @param crearUsuarioDTO Datos del nuevo usuario.
     * @return Detalles del usuario creado.
     */
    @Operation(summary = "Crear un nuevo usuario",
            description = "Crea un nuevo usuario a partir de los datos proporcionados.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Petición incorrecta")
            })
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<UsuarioDetallesDTO> registrarUsuario(@RequestBody @Valid CrearUsuarioDTO crearUsuarioDTO) {
        UsuarioDetallesDTO usuario = usuarioService.crearUsuario(crearUsuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    /**
     * Endpoint para actualizar los detalles de un usuario por su identificador.
     *
     * @param id Identificador del usuario.
     * @param modificarUsuarioDTO Datos actualizados del usuario.
     * @return Usuario actualizado en formato DTO.
     */
    @Operation(summary = "Actualizar un usuario",
            description = "Permite actualizar los detalles de un usuario existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
            })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<UsuarioDetallesDTO> actualizarUsuario(@PathVariable Long id, @RequestBody @Valid ModificarUsuarioDTO modificarUsuarioDTO) {
        UsuarioDetallesDTO usuario = usuarioService.editarUsuario(id, modificarUsuarioDTO);
        return ResponseEntity.ok(usuario);
    }

    /**
     * Endpoint para eliminar un usuario por su identificador.
     *
     * @param id Identificador del usuario a eliminar.
     * @return Respuesta vacía con código HTTP 204 si la eliminación fue exitosa.
     */
    @Operation(summary = "Eliminar un usuario",
            description = "Elimina un usuario del sistema utilizando su ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
            })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
