package daw2a.gestion_alimentos_api_rest.services;

import daw2a.gestion_alimentos_api_rest.dto.ubicacion.CrearUbicacionDTO;
import daw2a.gestion_alimentos_api_rest.dto.ubicacion.ModificarUbicacionDTO;
import daw2a.gestion_alimentos_api_rest.dto.ubicacion.UbicacionDTO;
import daw2a.gestion_alimentos_api_rest.dto.ubicacion.UbicacionEspacioDTO;
import daw2a.gestion_alimentos_api_rest.entities.Existencia;
import daw2a.gestion_alimentos_api_rest.entities.Ubicacion;
import daw2a.gestion_alimentos_api_rest.exceptions.RecursoNoEncontradoException;
import daw2a.gestion_alimentos_api_rest.repositories.ExistenciaRepository;
import daw2a.gestion_alimentos_api_rest.repositories.UbicacionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio encargado de gestionar las ubicaciones en el sistema,
 * incluyendo la creación, actualización, eliminación y consulta de ubicaciones.
 */
@Service
public class UbicacionService {
    private final UbicacionRepository ubicacionRepository;
    private final ExistenciaRepository existenciaRepository;

    public UbicacionService(UbicacionRepository ubicacionRepository, ExistenciaRepository existenciaRepository) {
        this.ubicacionRepository = ubicacionRepository;
        this.existenciaRepository = existenciaRepository;
    }

    /**
     * Listar todas las ubicaciones o las ubicaciones de un tipo específico.
     *
     * @param tipoUbicacion Tipo de ubicación (alacena, nevera o congelador).
     * @param pageable Objeto Pageable para la paginación de resultados.
     * @return Listado de ubicaciones en forma de una página.
     */
    public Page<?> listarUbicaciones(String tipoUbicacion, Pageable pageable) {
        Page<Ubicacion> ubicaciones;

        if (tipoUbicacion != null && !tipoUbicacion.isEmpty()) {
            ubicaciones = ubicacionRepository.findByTipoUbicacionContainingIgnoreCase(tipoUbicacion, pageable);
        } else {
            ubicaciones = ubicacionRepository.findAll(pageable);
        }

        return ubicaciones.map(this::convertirAUbicacionDTO);
    }

    /**
     * Obtener los detalles de una ubicación identificada por su id.
     *
     * @param id Identificador de la ubicación.
     * @return Detalles de la ubicación.
     * @throws RecursoNoEncontradoException Si no existe una ubicación con ese id.
     */
    public UbicacionDTO obtenerUbicacion(Long id) {
        Ubicacion ubicacion = ubicacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la ubicacion con el id " + id));

        return convertirAUbicacionDTO(ubicacion);
    }

    /**
     * Calcula el espacio total, el espacio ocupado y el espacio disponible para un tipo de ubicación específico.
     *
     * @param tipoUbicacion Tipo de ubicación (nevera, alacena o congelador).
     * @return Información sobre el espacio total, ocupado y disponible para el tipo de ubicación especificado.
     */
    public UbicacionEspacioDTO obtenerEscapcioPorTipoUbicacion(String tipoUbicacion) {
        List<Ubicacion> ubicaciones = ubicacionRepository.findByTipoUbicacionContainingIgnoreCase(tipoUbicacion, null).getContent();
        Long espacioTotal = ubicaciones.stream()
                .mapToLong(Ubicacion::getCapacidad)
                .sum();


        List<Existencia> existencias = existenciaRepository.findByUbicacion_TipoUbicacionIgnoreCase(tipoUbicacion, null).getContent();
        Long espacioOcupado = existencias.stream()
                .mapToLong(Existencia::getCantidad)
                .sum();

        Long espacioDisponible = espacioTotal - espacioOcupado;

        UbicacionEspacioDTO ubicacionEspacioDTO = new UbicacionEspacioDTO();
        ubicacionEspacioDTO.setTipoUbicacion(tipoUbicacion);
        ubicacionEspacioDTO.setEspacioDisponible(espacioDisponible);
        ubicacionEspacioDTO.setEspacioOcupado(espacioOcupado);
        ubicacionEspacioDTO.setEspacioTotal(espacioTotal);

        return ubicacionEspacioDTO;
    }

    /**
     * Crear una nueva ubicación en el sistema.
     *
     * @param crearUbicacionDTO Datos de la nueva ubicación a crear.
     * @return La ubicación creada.
     */
    public UbicacionDTO crearUbicacion(CrearUbicacionDTO crearUbicacionDTO) {
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setDescripcion(crearUbicacionDTO.getDescripcion());
        ubicacion.setTipoUbicacion(crearUbicacionDTO.getTipoUbicacion());
        ubicacion.setCapacidad(crearUbicacionDTO.getCapacidad());

        return convertirAUbicacionDTO(ubicacionRepository.save(ubicacion));
    }

    /**
     * Actualizar los datos de una ubicación existente.
     *
     * @param id Identificador de la ubicación a actualizar.
     * @param modificarUbicacionDTO Datos actualizados de la ubicación.
     * @return La ubicación actualizada.
     * @throws RecursoNoEncontradoException Si no existe una ubicación con ese id.
     */
    public UbicacionDTO editarUbicacion(Long id, ModificarUbicacionDTO modificarUbicacionDTO) {
        Ubicacion ubicacion = ubicacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la ubicacion con el id " + id));

        if (modificarUbicacionDTO.getDescripcion() != null && !modificarUbicacionDTO.getDescripcion().isEmpty()) {
            ubicacion.setDescripcion(modificarUbicacionDTO.getDescripcion());
        }
        if (modificarUbicacionDTO.getTipoUbicacion() != null && !modificarUbicacionDTO.getTipoUbicacion().isEmpty()) {
            ubicacion.setTipoUbicacion(modificarUbicacionDTO.getTipoUbicacion());
        }
        if (modificarUbicacionDTO.getCapacidad() != null) {
            ubicacion.setCapacidad(modificarUbicacionDTO.getCapacidad());
        }

        return convertirAUbicacionDTO(ubicacionRepository.save(ubicacion));
    }

    /**
     * Eliminar una ubicación del sistema.
     *
     * @param id Identificador de la ubicación a eliminar.
     * @throws RecursoNoEncontradoException Si no existe una ubicación con ese id.
     */
    public void eliminarUbicacion(Long id) {
        Ubicacion ubicacion = ubicacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la ubicacion con el id " + id));

        ubicacionRepository.delete(ubicacion);
    }

    /**
     * Convertir una entidad Ubicacion a un DTO UbicacionDTO.
     *
     * @param ubicacion Entidad de la ubicación.
     * @return El DTO correspondiente a la ubicación.
     */
    private UbicacionDTO convertirAUbicacionDTO(Ubicacion ubicacion) {
        UbicacionDTO ubicacionDTO = new UbicacionDTO();
        ubicacionDTO.setId(ubicacion.getId());
        ubicacionDTO.setDescripcion(ubicacion.getDescripcion());
        ubicacionDTO.setTipoUbicacion(ubicacion.getTipoUbicacion());
        ubicacionDTO.setCapacidad(ubicacion.getCapacidad());

        return ubicacionDTO;
    }
}
