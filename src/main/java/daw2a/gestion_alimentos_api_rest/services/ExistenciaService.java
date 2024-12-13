package daw2a.gestion_alimentos_api_rest.services;

import daw2a.gestion_alimentos_api_rest.dto.existencia.CrearExistenciaDTO;
import daw2a.gestion_alimentos_api_rest.dto.existencia.ExistenciaDTO;
import daw2a.gestion_alimentos_api_rest.dto.existencia.ModificarExistenciaDTO;
import daw2a.gestion_alimentos_api_rest.entities.Alimento;
import daw2a.gestion_alimentos_api_rest.entities.Existencia;
import daw2a.gestion_alimentos_api_rest.entities.Ubicacion;
import daw2a.gestion_alimentos_api_rest.exceptions.RecursoNoEncontradoException;
import daw2a.gestion_alimentos_api_rest.repositories.AlimentoRepository;
import daw2a.gestion_alimentos_api_rest.repositories.ExistenciaRepository;
import daw2a.gestion_alimentos_api_rest.repositories.UbicacionRepository;
import daw2a.gestion_alimentos_api_rest.repositories.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ExistenciaService {
    private final ExistenciaRepository existenciaRepository;
    private final AlimentoRepository alimentoRepository;
    private final UbicacionRepository ubicacionRepository;

    public ExistenciaService(ExistenciaRepository existenciaRepository, AlimentoRepository alimentoRepository, UbicacionRepository ubicacionRepository) {
        this.existenciaRepository = existenciaRepository;
        this.alimentoRepository = alimentoRepository;
        this.ubicacionRepository = ubicacionRepository;
    }

    /**
     * Listar todas las existencias
     * @param pageable Pageable
     * @return Listado de existencias
     */
    public Page<ExistenciaDTO> listarExistencias(Pageable pageable) {
        return existenciaRepository.findAll(pageable).map(this::convertirAExistenciaDTO);
    }

    /**
     * Obtener los detalles de una existencia
     * @param id Identificador de la existencia
     * @return Existencia
     * @throws RecursoNoEncontradoException si no existe una existencia con el id dado
     */
    public ExistenciaDTO consultarExistencia(Long id) {
        Existencia existencia = existenciaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Existencia con el id " + id + " no encontrada"));

        return convertirAExistenciaDTO(existencia);
    }

    /**
     * Registrar una existencia nueva
     * @param nuevaExistencia Datos de la nueva existencia
     * @return Datos de la nueva existencia
     * @throws RecursoNoEncontradoException si el id del alimento o el id de la ubicacion no existe
     */
    @Transactional
    public ExistenciaDTO crearExistencia(CrearExistenciaDTO nuevaExistencia) {
        Alimento alimento = alimentoRepository.findById(nuevaExistencia.getIdAlimento())
                .orElseThrow(() -> new RecursoNoEncontradoException("Alimento con el id " + nuevaExistencia.getIdAlimento() + " no encontrado"));

        Ubicacion ubicacion = ubicacionRepository.findById(nuevaExistencia.getIdUbicacion())
                .orElseThrow(() -> new RecursoNoEncontradoException("Ubicacion con id " + nuevaExistencia.getIdUbicacion() + " no encontrada"));

        Existencia existencia = Existencia.builder()
                .alimento(alimento)
                .ubicacion(ubicacion)
                .cantidad(nuevaExistencia.getCantidad())
                .fechaEntrada(LocalDate.now())
                .build();

        existenciaRepository.save(existencia);
        return convertirAExistenciaDTO(existencia);
    }

    /**
     * Actualizar la cantidad de la existencia
     * @param id Identificador de la existencia
     * @param modificarExistenciaDTO Dato que se va ha modificar
     * @return Existencia actualizada
     * @throws RecursoNoEncontradoException Si no hay una existencia con ese id
     */
    @Transactional
    public ExistenciaDTO actualizarCantidad(Long id, ModificarExistenciaDTO modificarExistenciaDTO) {
        Existencia existencia = existenciaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Existencia con el id " + id + " no encontrada"));

        existencia.setCantidad(modificarExistenciaDTO.getCantidad());
        return convertirAExistenciaDTO(existenciaRepository.save(existencia));
    }

    /**
     * Eliminar una existencia
     * @param id Identificador de la existencia
     * @throws RecursoNoEncontradoException si no hay una existencia con ese id
     */
    public void eliminarExistencia(Long id) {
        Existencia existencia = existenciaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Existencia con el id " + id + " no encontrada"));

        existenciaRepository.delete(existencia);
    }

    /**
     * Convertir de Existencia a ExistenciaDTO
     * @param existencia Entidad
     * @return DTO
     */
    private ExistenciaDTO convertirAExistenciaDTO(Existencia existencia) {
        ExistenciaDTO existenciaDTO = new ExistenciaDTO();
        existenciaDTO.setId(existencia.getId());
        existenciaDTO.setIdAlimento(existencia.getAlimento().getId());
        existenciaDTO.setNombreAlimento(existencia.getAlimento().getNombre());
        existenciaDTO.setIdUbicacion(existencia.getUbicacion().getId());
        existenciaDTO.setDescripcionUbicacion(existencia.getUbicacion().getDescripcion());
        existenciaDTO.setCantidad(existencia.getCantidad());
        existenciaDTO.setFechaEntrada(existencia.getFechaEntrada());

        return existenciaDTO;
    }
}
