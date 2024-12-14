package daw2a.gestion_alimentos_api_rest.controllers;

import daw2a.gestion_alimentos_api_rest.dto.alimento.AlimentoDTO;
import daw2a.gestion_alimentos_api_rest.dto.alimento.CrearAlimentoDTO;
import daw2a.gestion_alimentos_api_rest.dto.alimento.ModificarAlimentoDTO;
import daw2a.gestion_alimentos_api_rest.services.AlimentoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/alimentos")
public class AlimentoController {
    private final AlimentoService alimentoService;

    public AlimentoController(AlimentoService alimentoService) {
        this.alimentoService = alimentoService;
    }

    /**
     * Listar todos los alimentos o por nombre
     * @param nombre Nombre que deben contener los alimentos
     * @param pageable Pageable
     * @return Listado de alimentos
     */
    @GetMapping
    public ResponseEntity<Page<?>> listarAlimentos(@RequestParam(required = false) String nombre, Pageable pageable) {
        Page<?> alimentos = alimentoService.listarAlimentos(nombre, pageable);
        return ResponseEntity.ok(alimentos);
    }

    @GetMapping("/caducan")
    public ResponseEntity<Page<AlimentoDTO>> listarAlimentosCaducan(Pageable pageable) {
        Page<AlimentoDTO> alimentosCaducan = alimentoService.listarEntreFechasCaducidad(LocalDate.now(),LocalDate.now().plusWeeks(1), pageable);
        return ResponseEntity.ok(alimentosCaducan);
    }

    /**
     * Obtener los detalles de un alimento
     * @param id Identificador
     * @return Alimento
     */
    @GetMapping("/{id}")
    public ResponseEntity<AlimentoDTO> obtenerAlimento(@PathVariable Long id) {
        AlimentoDTO alimento = alimentoService.obtenerAlimento(id);
        return ResponseEntity.ok(alimento);
    }

    /**
     * Guardar un alimento nuevo
     * @param crearAlimentoDTO DTO con los datos necesarios
     * @return Alimento guardado
     */
    @PostMapping
    public ResponseEntity<AlimentoDTO> guardarAlimento(@RequestBody @Valid CrearAlimentoDTO crearAlimentoDTO) {
        AlimentoDTO nuevoAlimento = alimentoService.crearAlimento(crearAlimentoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAlimento);
    }

    /**
     * Actualizar los datos de un alimento existente
     * @param id Identificador de alimento
     * @param modificarAlimentoDTO Datos nuevos
     * @return Alimento actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<AlimentoDTO> actualizarAlimento(@PathVariable Long id, @RequestBody @Valid ModificarAlimentoDTO modificarAlimentoDTO) {
        return ResponseEntity.ok(alimentoService.actualizarAlimento(id, modificarAlimentoDTO));
    }

    /**
     * Eliminar un alimento
     * @param id Identificador del alimento
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAlimento(@PathVariable Long id) {
        alimentoService.eliminarAlimento(id);
        return ResponseEntity.noContent().build();
    }
}
