package daw2a.gestion_alimentos_api_rest.controllers;

import daw2a.gestion_alimentos_api_rest.dto.ubicacion.CrearUbicacionDTO;
import daw2a.gestion_alimentos_api_rest.dto.ubicacion.ModificarUbicacionDTO;
import daw2a.gestion_alimentos_api_rest.dto.ubicacion.UbicacionDTO;
import daw2a.gestion_alimentos_api_rest.dto.ubicacion.UbicacionEspacioDTO;
import daw2a.gestion_alimentos_api_rest.services.UbicacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gestionar las ubicaciones dentro del sistema.
 * Este controlador expone los endpoints para crear, modificar, listar y obtener detalles de las ubicaciones.
 */
@RestController
@RequestMapping("/ubicaciones")
public class UbicacionController {
    private final UbicacionService ubicacionService;

    /**
     * Constructor para inyectar el servicio de ubicaciones.
     * @param ubicacionService Servicio para gestionar operaciones de ubicación.
     */
    public UbicacionController(UbicacionService ubicacionService) {
        this.ubicacionService = ubicacionService;
    }

    /**
     * Endpoint para listar todas las ubicaciones o las que sean de un tipo específico.
     *
     * @param tipoUbicacion Tipo de ubicación (por ejemplo: alacena, nevera, congelador).
     * @param pageable Configuración de paginación.
     * @return Lista paginada de ubicaciones.
     */
    @Operation(summary = "Listar ubicaciones",
            description = "Recupera una lista de ubicaciones con soporte para filtrado por tipo y paginación.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de ubicaciones obtenida exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Petición incorrecta")
            })
    @GetMapping
    public ResponseEntity<Page<?>> listarUbicaciones(@RequestParam(required = false) String tipoUbicacion, Pageable pageable) {
        Page<?> ubicaciones = ubicacionService.listarUbicaciones(tipoUbicacion, pageable);
        return ResponseEntity.ok(ubicaciones);
    }

    /**
     * Endpoint para obtener los detalles de una ubicación específica.
     *
     * @param id Identificador de la ubicación.
     * @return Detalles de la ubicación.
     */
    @Operation(summary = "Obtener detalles de una ubicación",
            description = "Recupera los detalles de una ubicación específica utilizando su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Detalles de la ubicación obtenidos exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Ubicación no encontrada")
            })
    @GetMapping("/{id}")
    public ResponseEntity<UbicacionDTO> obtenerUbicacion(@PathVariable Long id) {
        UbicacionDTO ubicacion = ubicacionService.obtenerUbicacion(id);
        return ResponseEntity.ok(ubicacion);
    }

    /**
     * Endpoint para obtener un informe sobre el espacio disponible y ocupado de un tipo de ubicación.
     *
     * @param tipoUbicacion Tipo de ubicación (por ejemplo: nevera, congelador, alacena).
     * @return Detalles del espacio disponible y ocupado en la ubicación.
     */
    @Operation(summary = "Obtener espacio de una ubicación por tipo",
            description = "Recupera información sobre la capacidad total, ocupada y disponible de una ubicación por tipo.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Espacio de ubicación obtenido exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Ubicación no encontrada")
            })
    @GetMapping("/espacio/{tipoUbicacion}")
    public ResponseEntity<UbicacionEspacioDTO> obtenerEscapcioPorTipoUbicacion(@PathVariable String tipoUbicacion) {
        UbicacionEspacioDTO ubicacionEspacioDTO = ubicacionService.obtenerEscapcioPorTipoUbicacion(tipoUbicacion);
        return ResponseEntity.ok(ubicacionEspacioDTO);
    }

    /**
     * Endpoint para crear una nueva ubicación.
     *
     * @param crearUbicacionDTO Datos de la nueva ubicación a crear.
     * @return La ubicación creada.
     */
    @Operation(summary = "Crear una nueva ubicación",
            description = "Crea una nueva ubicación con los datos proporcionados.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Ubicación creada exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Petición incorrecta")
            })
    @PostMapping
    public ResponseEntity<UbicacionDTO> crearUbicacion(@RequestBody @Valid CrearUbicacionDTO crearUbicacionDTO) {
        UbicacionDTO nuevaUbicacion = ubicacionService.crearUbicacion(crearUbicacionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaUbicacion);
    }

    /**
     * Endpoint para actualizar los datos de una ubicación existente.
     *
     * @param id Identificador de la ubicación a actualizar.
     * @param modificarUbicacionDTO Nuevos datos de la ubicación.
     * @return La ubicación actualizada.
     */
    @Operation(summary = "Actualizar ubicación",
            description = "Permite actualizar los datos de una ubicación existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ubicación actualizada exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Ubicación no encontrada")
            })
    @PutMapping("/{id}")
    public ResponseEntity<UbicacionDTO> actualizarUbicacion(@PathVariable Long id, @RequestBody @Valid ModificarUbicacionDTO modificarUbicacionDTO) {
        return ResponseEntity.ok(ubicacionService.editarUbicacion(id, modificarUbicacionDTO));
    }

    /**
     * Endpoint para eliminar una ubicación por su identificador.
     *
     * @param id Identificador de la ubicación a eliminar.
     * @return Respuesta vacía con código HTTP 204 si la eliminación fue exitosa.
     */
    @Operation(summary = "Eliminar una ubicación",
            description = "Elimina una ubicación existente del sistema.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Ubicación eliminada exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Ubicación no encontrada")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUbicacion(@PathVariable Long id) {
        ubicacionService.eliminarUbicacion(id);
        return ResponseEntity.noContent().build();
    }
}
