package daw2a.gestion_alimentos_api_rest.controllers;

import daw2a.gestion_alimentos_api_rest.dto.alimento.AlimentoDTO;
import daw2a.gestion_alimentos_api_rest.dto.alimento.CrearAlimentoDTO;
import daw2a.gestion_alimentos_api_rest.dto.alimento.ModificarAlimentoDTO;
import daw2a.gestion_alimentos_api_rest.services.AlimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * Controlador para gestionar alimentos en el sistema.
 * <p>Este controlador ofrece endpoints para realizar operaciones CRUD sobre los alimentos almacenados.</p>
 */
@RestController
@RequestMapping("/alimentos")
public class AlimentoController {
    private final AlimentoService alimentoService;

    public AlimentoController(AlimentoService alimentoService) {
        this.alimentoService = alimentoService;
    }

    /**
     * Listar todos los alimentos o por nombre
     * <p>Este endpoint devuelve una lista de alimentos, con la opción de filtrar por nombre.</p>
     *
     * @param nombre Nombre del alimento a buscar (opcional)
     * @param pageable Parámetros de paginación
     * @return Listado de alimentos encontrados
     */
    @Operation(summary = "Listar alimentos",
            description = "Obtiene un listado de los alimentos o filtra por nombre si se proporciona")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de alimentos obtenido correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    })
    @GetMapping
    public ResponseEntity<Page<?>> listarAlimentos(@RequestParam(required = false) String nombre, Pageable pageable) {
        Page<?> alimentos = alimentoService.listarAlimentos(nombre, pageable);
        return ResponseEntity.ok(alimentos);
    }

    /**
     * Listar alimentos próximos a caducar.
     * <p>Este endpoint devuelve los alimentos que caducan en la próxima semana.</p>
     *
     * @param pageable Parámetros de paginación.
     * @return Lisatdo alimentos próximos a caducar.
     */
    @Operation(summary = "Listar alimentos próximos a caducar", description = "Obtiene un listado de alimentos que caducan en la próxima semana.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de alimentos próximos a caducar",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    })
    @GetMapping("/caducan")
    public ResponseEntity<Page<AlimentoDTO>> listarAlimentosCaducan(Pageable pageable) {
        Page<AlimentoDTO> alimentosCaducan = alimentoService.listarEntreFechasCaducidad(LocalDate.now(),LocalDate.now().plusWeeks(1), pageable);
        return ResponseEntity.ok(alimentosCaducan);
    }

    /**
     * Obtener los detalles de un alimento.
     * <p>Este endpoint devuelve los detalles de un alimento a partir de su identificador.</p>
     *
     * @param id Identificador único del alimento.
     * @return Detalles del alimento.
     */
    @Operation(summary = "Obtener un alimento", description = "Devuelve los detalles de un alimento a partir de su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalles del alimento",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AlimentoDTO.class))),
            @ApiResponse(responseCode = "404", description = "El alimento no se ha encontrado",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<AlimentoDTO> obtenerAlimento(@PathVariable Long id) {
        AlimentoDTO alimento = alimentoService.obtenerAlimento(id);
        return ResponseEntity.ok(alimento);
    }

    /**
     * Crear un nuevo alimento.
     * <p>Este endpoint permite crear un nuevo alimento en el sistema.</p>
     *
     * @param crearAlimentoDTO Objeto que contiene los datos para crear el alimento.
     * @return El alimento creado.
     */
    @Operation(summary = "Crear un nuevo alimento", description = "Crea un nuevo alimento en el sistema con los datos proporcionados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alimento creado con éxito",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AlimentoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Los datos proporcionados no son válidos",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<AlimentoDTO> guardarAlimento(@RequestBody @Valid CrearAlimentoDTO crearAlimentoDTO) {
        AlimentoDTO nuevoAlimento = alimentoService.crearAlimento(crearAlimentoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAlimento);
    }

    /**
     * Actualizar los datos de un alimento existente.
     * <p>Este endpoint permite actualizar los datos de un alimento previamente registrado en el sistema.</p>
     *
     * @param id Identificador único del alimento a actualizar.
     * @param modificarAlimentoDTO Objeto con los nuevos datos del alimento.
     * @return El alimento actualizado.
     */
    @Operation(summary = "Actualizar un alimento", description = "Actualiza los datos de un alimento existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alimento actualizado correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AlimentoDTO.class))),
            @ApiResponse(responseCode = "404", description = "El alimento no se ha encontrado",
                    content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/{id}")
    public ResponseEntity<AlimentoDTO> actualizarAlimento(@PathVariable Long id, @RequestBody @Valid ModificarAlimentoDTO modificarAlimentoDTO) {
        return ResponseEntity.ok(alimentoService.actualizarAlimento(id, modificarAlimentoDTO));
    }

    /**
     * Eliminar un alimento.
     * <p>Este endpoint permite eliminar un alimento de la base de datos.</p>
     *
     * @param id Identificador único del alimento a eliminar.
     * @return Una respuesta vacía con el código 204 (No Content).
     */
    @Operation(summary = "Eliminar un alimento", description = "Elimina un alimento del sistema a partir de su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Alimento eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "El alimento no se encontró",
                    content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAlimento(@PathVariable Long id) {
        alimentoService.eliminarAlimento(id);
        return ResponseEntity.noContent().build();
    }
}
