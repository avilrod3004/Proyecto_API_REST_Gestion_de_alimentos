package daw2a.gestion_alimentos_api_rest.controllers;

import daw2a.gestion_alimentos_api_rest.dto.ubicacion.CrearUbicacionDTO;
import daw2a.gestion_alimentos_api_rest.dto.ubicacion.ModificarUbicacionDTO;
import daw2a.gestion_alimentos_api_rest.dto.ubicacion.UbicacionDTO;
import daw2a.gestion_alimentos_api_rest.dto.ubicacion.UbicacionEspacioDTO;
import daw2a.gestion_alimentos_api_rest.services.UbicacionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ubicaciones")
public class UbicacionController {
    private final UbicacionService ubicacionService;

    public UbicacionController(UbicacionService ubicacionService) {
        this.ubicacionService = ubicacionService;
    }

    /**
     * Listar todas las ubicaciones o las que sean de un tipo
     * @param tipoUbicacion Tipo de ubicación (alacena, nevera y congelador)
     * @param pageable Pageable
     * @return Listado de ubicaciones
     */
    @GetMapping
    public ResponseEntity<Page<?>> listarUbicaciones(@RequestParam(required = false) String tipoUbicacion, Pageable pageable) {
        Page<?> ubicaciones = ubicacionService.listarUbicaciones(tipoUbicacion, pageable);
        return ResponseEntity.ok(ubicaciones);
    }

    /**
     * Obtener los detalles de una ubicacion especifica
     * @param id Identificador de la ubicación
     * @return Ubicación
     */
    @GetMapping("/{id}")
    public ResponseEntity<UbicacionDTO> obtenerUbicacion(@PathVariable Long id) {
        UbicacionDTO ubicacion = ubicacionService.obtenerUbicacion(id);
        return ResponseEntity.ok(ubicacion);
    }

    /**
     * Informe de la capacidad total de un tipo de ubicación, la capacidad ocupada y la parte disponible
     * @param tipoUbicacion Tipo de ubicacion (nevera, congelador y alacena)
     * @return Espacio del tipo de ubicación
     */
    @GetMapping("/espacio/{tipoUbicacion}")
    public ResponseEntity<UbicacionEspacioDTO> obtenerEscapcioPorTipoUbicacion(@PathVariable String tipoUbicacion) {
        UbicacionEspacioDTO ubicacionEspacioDTO = ubicacionService.obtenerEscapcioPorTipoUbicacion(tipoUbicacion);
        return ResponseEntity.ok(ubicacionEspacioDTO);
    }

    /**
     * Crear una ubicacion nueva
     * @param crearUbicacionDTO Datos de la nueva ubicacion
     * @return Ubicacion creada
     */
    @PostMapping
    public ResponseEntity<UbicacionDTO> crearUbicacion(@RequestBody @Valid CrearUbicacionDTO crearUbicacionDTO) {
        UbicacionDTO nuevaUbicacion = ubicacionService.crearUbicacion(crearUbicacionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaUbicacion);
    }

    /**
     * Actualizar los datos de una ubicación
     * @param id Identificador de la ubicación
     * @param modificarUbicacionDTO Datos nuevos
     * @return Ubicación actualizada
     */
    @PutMapping("/{id}")
    public ResponseEntity<UbicacionDTO> actualizarUbicacion(@PathVariable Long id, @RequestBody @Valid ModificarUbicacionDTO modificarUbicacionDTO) {
        return ResponseEntity.ok(ubicacionService.editarUbicacion(id, modificarUbicacionDTO));
    }

    /**
     * Eliminar una ubicacion
     * @param id Identificador de la ubicación
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUbicacion(@PathVariable Long id) {
        ubicacionService.eliminarUbicacion(id);
        return ResponseEntity.noContent().build();
    }
}
