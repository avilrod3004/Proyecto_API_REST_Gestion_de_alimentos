package daw2a.gestion_alimentos_api_rest.controllers;

import daw2a.gestion_alimentos_api_rest.dto.existencia.*;
import daw2a.gestion_alimentos_api_rest.entities.Existencia;
import daw2a.gestion_alimentos_api_rest.services.ExistenciaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.css.ElementCSSInlineStyle;

import java.time.LocalDate;

@RestController
@RequestMapping("/existencias")
public class ExistenciaController {
    private final ExistenciaService existenciaService;

    public ExistenciaController(ExistenciaService existenciaService) {
        this.existenciaService = existenciaService;
    }

    /**
     * Listar todas las existencias, por id de Alimento y/o por id de la ubicación
     * @param idAlimento Identificador del alimento
     * @param idUbicacion Identificador de la ubicacion
     * @param pageable Pageable
     * @return Listado de las existencias
     */
    @GetMapping
    public ResponseEntity<Page<ExistenciaDTO>> listarExistencias(@RequestParam(required = false) Long idAlimento, @RequestParam(required = false) Long idUbicacion, Pageable pageable) {
        Page<ExistenciaDTO> existencias = existenciaService.listarExistencias(idAlimento, idUbicacion, pageable);
        return ResponseEntity.ok(existencias);
    }

    /**
     * Listar las existencias proximas a caducar agrupadas por ubicación
     * @param size Número de existencias por página
     * @return Listado de existencias
     */
    @GetMapping("/caducan/{size}")
    public ResponseEntity<Page<ExistenciaDetallesDTO>> listarCaducanPorUbicacion(@PathVariable int size) {
        Page<ExistenciaDetallesDTO> existencias = existenciaService.listadoCaducanPorUbicacion(size, LocalDate.now(), LocalDate.now().plusWeeks(2));
        return ResponseEntity.ok(existencias);
    }

    /**
     * Obtener los detalles de una existencia
     * @param id Identificador de la existencia
     * @return Existencia
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExistenciaDTO> buscarExistencia(@PathVariable Long id) {
        ExistenciaDTO existenciaDTO = existenciaService.consultarExistencia(id);
        return ResponseEntity.ok(existenciaDTO);
    }

    /**
     * Registrar una existencia nueva
     * @param crearExistenciaDTO DTO con los datos de la nueva existencia
     * @return Existencia nueva
     */
    @PostMapping
    public ResponseEntity<ExistenciaDTO> registrarExistencia(@RequestBody CrearExistenciaDTO crearExistenciaDTO) {
        ExistenciaDTO nuevaExistencia = existenciaService.crearExistencia(crearExistenciaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaExistencia);
    }

    /**
     * Actualizar la cantidad de una existencia
     * @param id Identificador de la existencia
     * @param modificarExistenciaDTO Cantidad nueva
     * @return Existencia actualizada
     */
    @PutMapping("/{id}")
    public ResponseEntity<ExistenciaDTO> actualizarExistencia(@PathVariable Long id, @RequestBody @Valid ModificarExistenciaDTO modificarExistenciaDTO) {
        ExistenciaDTO existenciaDTO = existenciaService.actualizarCantidad(id, modificarExistenciaDTO);
        return ResponseEntity.ok(existenciaDTO);
    }

    /**
     * Mover una existencia de ubicación
     * @param id Identificador de la existencia
     * @param moverExistenciaDTO Identificador de la nueva ubicacion
     * @return Existencia actualizada
     */
    @PutMapping("/mover/{id}")
    public ResponseEntity<ExistenciaDTO> moverExistencia(@PathVariable Long id, @RequestBody @Valid MoverExistenciaDTO moverExistenciaDTO) {
        ExistenciaDTO existenciaDTO = existenciaService.moverExistencia(id, moverExistenciaDTO);
        return ResponseEntity.ok(existenciaDTO);
    }

    /**
     * Endpoint para consumir una cantidad de un alimento en una ubicación.
     * @param idAlimento Id del alimento
     * @param idUbicacion Id de la ubicación
     * @param cantidad Cantidad a consumir
     * @return Existencia actualizada
     */
    @PostMapping("/consumir")
    public ResponseEntity<ExistenciaDTO> consumirExistencia(@RequestParam Long idAlimento, @RequestParam Long idUbicacion, @RequestParam Long cantidad) {
        ExistenciaDTO existenciaActualizada = existenciaService.consumirExistencia(idAlimento, idUbicacion, cantidad);
        return ResponseEntity.ok(existenciaActualizada);
    }

    /**
     * Eliminar una existencia
     * @param id Identificador de la existencia
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarExistencia(@PathVariable Long id) {
        existenciaService.eliminarExistencia(id);
        return ResponseEntity.noContent().build();
    }
}
