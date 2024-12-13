package daw2a.gestion_alimentos_api_rest.controllers;

import daw2a.gestion_alimentos_api_rest.dto.existencia.CrearExistenciaDTO;
import daw2a.gestion_alimentos_api_rest.dto.existencia.ExistenciaDTO;
import daw2a.gestion_alimentos_api_rest.dto.existencia.ModificarExistenciaDTO;
import daw2a.gestion_alimentos_api_rest.entities.Existencia;
import daw2a.gestion_alimentos_api_rest.services.ExistenciaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/existencias")
public class ExistenciaController {
    private final ExistenciaService existenciaService;

    public ExistenciaController(ExistenciaService existenciaService) {
        this.existenciaService = existenciaService;
    }

    /**
     * Listar todas las existencias
     * @param pageable Pageable
     * @return Listado de las existencias
     */
    @GetMapping
    public ResponseEntity<Page<ExistenciaDTO>> listarExistencias(Pageable pageable) {
        Page<ExistenciaDTO> existencias = existenciaService.listarExistencias(pageable);
        return ResponseEntity.ok(existencias);
    }

    /**
     * Obtener los detalles de un existencia
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
