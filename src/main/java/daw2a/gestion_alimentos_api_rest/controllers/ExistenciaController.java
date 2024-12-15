package daw2a.gestion_alimentos_api_rest.controllers;

import daw2a.gestion_alimentos_api_rest.dto.existencia.*;
import daw2a.gestion_alimentos_api_rest.services.ExistenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * Controlador REST para gestionar las existencias de alimentos en el sistema.
 * Este controlador expone los endpoints para registrar, actualizar, eliminar y consultar las existencias.
 */
@RestController
@RequestMapping("/existencias")
public class ExistenciaController {
    private final ExistenciaService existenciaService;

    /**
     * Constructor para inyectar el servicio de existencias.
     * @param existenciaService Servicio para gestionar las operaciones de existencias.
     */
    public ExistenciaController(ExistenciaService existenciaService) {
        this.existenciaService = existenciaService;
    }

    /**
     * Endpoint para listar todas las existencias, con posibilidad de filtrado por alimento y/o ubicación.
     *
     * @param idAlimento Identificador del alimento.
     * @param idUbicacion Identificador de la ubicación.
     * @param pageable Configuración de paginación.
     * @return Lista de existencias paginadas.
     */
    @Operation(summary = "Listar existencias",
            description = "Recupera una lista de existencias con soporte para filtrado por alimento, ubicación y paginación.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de existencias obtenida exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Petición incorrecta")
            })
    @GetMapping
    public ResponseEntity<Page<ExistenciaDTO>> listarExistencias(@RequestParam(required = false) Long idAlimento, @RequestParam(required = false) Long idUbicacion, Pageable pageable) {
        Page<ExistenciaDTO> existencias = existenciaService.listarExistencias(idAlimento, idUbicacion, pageable);
        return ResponseEntity.ok(existencias);
    }

    /**
     * Endpoint para listar las existencias próximas a caducar, agrupadas por ubicación.
     *
     * @param size Número de existencias por página.
     * @return Listado de existencias próximas a caducar.
     */
    @Operation(summary = "Listar existencias próximas a caducar",
            description = "Recupera una lista de existencias próximas a caducar, agrupadas por ubicación.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de existencias próximas a caducar obtenida exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Petición incorrecta")
            })
    @GetMapping("/caducan/{size}")
    public ResponseEntity<Page<ExistenciaDetallesDTO>> listarCaducanPorUbicacion(@PathVariable int size) {
        Page<ExistenciaDetallesDTO> existencias = existenciaService.listadoCaducanPorUbicacion(size, LocalDate.now(), LocalDate.now().plusWeeks(2));
        return ResponseEntity.ok(existencias);
    }

    /**
     * Endpoint para obtener los detalles de una existencia específica.
     *
     * @param id Identificador de la existencia.
     * @return Detalles de la existencia.
     */
    @Operation(summary = "Obtener detalles de una existencia",
            description = "Recupera los detalles de una existencia utilizando su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Detalles de la existencia obtenidos exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Existencia no encontrada")
            })
    @GetMapping("/{id}")
    public ResponseEntity<ExistenciaDTO> buscarExistencia(@PathVariable Long id) {
        ExistenciaDTO existenciaDTO = existenciaService.consultarExistencia(id);
        return ResponseEntity.ok(existenciaDTO);
    }

    /**
     * Endpoint para registrar una nueva existencia.
     *
     * @param crearExistenciaDTO Datos de la nueva existencia a registrar.
     * @return Existencia recién creada.
     */
    @Operation(summary = "Registrar nueva existencia",
            description = "Registra una nueva existencia con los datos proporcionados.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Existencia registrada exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Petición incorrecta")
            })
    @PostMapping
    public ResponseEntity<ExistenciaDTO> registrarExistencia(@RequestBody CrearExistenciaDTO crearExistenciaDTO) {
        ExistenciaDTO nuevaExistencia = existenciaService.crearExistencia(crearExistenciaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaExistencia);
    }

    /**
     * Endpoint para actualizar la cantidad de una existencia.
     *
     * @param id Identificador de la existencia a actualizar.
     * @param modificarExistenciaDTO Nueva cantidad para la existencia.
     * @return Existencia actualizada.
     */
    @Operation(summary = "Actualizar cantidad de existencia",
            description = "Permite actualizar la cantidad de una existencia.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cantidad de existencia actualizada exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Existencia no encontrada")
            })
    @PutMapping("/{id}")
    public ResponseEntity<ExistenciaDTO> actualizarExistencia(@PathVariable Long id, @RequestBody @Valid ModificarExistenciaDTO modificarExistenciaDTO) {
        ExistenciaDTO existenciaDTO = existenciaService.actualizarCantidad(id, modificarExistenciaDTO);
        return ResponseEntity.ok(existenciaDTO);
    }

    /**
     * Endpoint para mover una existencia a una nueva ubicación.
     *
     * @param id Identificador de la existencia a mover.
     * @param moverExistenciaDTO Datos de la nueva ubicación.
     * @return Existencia actualizada con la nueva ubicación.
     */
    @Operation(summary = "Mover existencia",
            description = "Permite mover una existencia a una nueva ubicación.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Existencia movida exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Existencia no encontrada")
            })
    @PutMapping("/mover/{id}")
    public ResponseEntity<ExistenciaDTO> moverExistencia(@PathVariable Long id, @RequestBody @Valid MoverExistenciaDTO moverExistenciaDTO) {
        ExistenciaDTO existenciaDTO = existenciaService.moverExistencia(id, moverExistenciaDTO);
        return ResponseEntity.ok(existenciaDTO);
    }

    /**
     * Endpoint para consumir una cantidad de un alimento en una ubicación.
     *
     * @param idAlimento Identificador del alimento.
     * @param idUbicacion Identificador de la ubicación.
     * @param cantidad Cantidad del alimento a consumir.
     * @return Existencia actualizada.
     */
    @Operation(summary = "Consumir existencia",
            description = "Permite consumir una cantidad específica de un alimento en una ubicación.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Existencia consumida exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Existencia no encontrada")
            })
    @PostMapping("/consumir")
    public ResponseEntity<ExistenciaDTO> consumirExistencia(@RequestParam Long idAlimento, @RequestParam Long idUbicacion, @RequestParam Long cantidad) {
        ExistenciaDTO existenciaActualizada = existenciaService.consumirExistencia(idAlimento, idUbicacion, cantidad);
        return ResponseEntity.ok(existenciaActualizada);
    }

    /**
     * Endpoint para eliminar una existencia.
     *
     * @param id Identificador de la existencia a eliminar.
     * @return Respuesta vacía con código HTTP 204 si la eliminación fue exitosa.
     */
    @Operation(summary = "Eliminar existencia",
            description = "Elimina una existencia del sistema.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Existencia eliminada exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Existencia no encontrada")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarExistencia(@PathVariable Long id) {
        existenciaService.eliminarExistencia(id);
        return ResponseEntity.noContent().build();
    }
}
