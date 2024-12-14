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

@Service
public class UbicacionService {
    private final UbicacionRepository ubicacionRepository;
    private final ExistenciaRepository existenciaRepository;

    public UbicacionService(UbicacionRepository ubicacionRepository, ExistenciaRepository existenciaRepository) {
        this.ubicacionRepository = ubicacionRepository;
        this.existenciaRepository = existenciaRepository;
    }

    /**
     * Listar todas las ubicaciones o las ubicaciones de un tipo de ubicacion
     * @param tipoUbicacion Tipo de ubicacion (alacena, nevera o congelador)
     * @param pageable Pageable
     * @return Listado de las ubicaciones
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
     * Obtener los detalles de una ubicación identificandola por su id
     * @param id Identificador de la ubicación
     * @return Detalles de la ubicación
     * @throws RecursoNoEncontradoException Si no hay una ubicación con esa id
     */
    public UbicacionDTO obtenerUbicacion(Long id) {
        Ubicacion ubicacion = ubicacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la ubicacion con el id " + id));

        return convertirAUbicacionDTO(ubicacion);
    }

    /**
     * Calcula la cantidad total, el espacio ocupado y disponible de un tipo de ubicación
     * @param tipoUbicacion Tipo de ubicación (nevera, alacena o congelador)
     * @return Espacio del tipo de ubicacion
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
     * Crear una ubicacion nueva
     * @param crearUbicacionDTO Detalles de la nueva ubicación
     * @return Ubicación creada
     */
    public UbicacionDTO crearUbicacion(CrearUbicacionDTO crearUbicacionDTO) {
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setDescripcion(crearUbicacionDTO.getDescripcion());
        ubicacion.setTipoUbicacion(crearUbicacionDTO.getTipoUbicacion());
        ubicacion.setCapacidad(crearUbicacionDTO.getCapacidad());

        return convertirAUbicacionDTO(ubicacionRepository.save(ubicacion));
    }

    /**
     * Actualizar los datos de una ubicación existente
     * @param id Identificador de la ubicacion
     * @param modificarUbicacionDTO Datos actualizados
     * @return Ubicacion actualizada
     * @throws RecursoNoEncontradoException Si no hay una ubicación con esa id
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
     * Eliminar ubicación
     * @param id Identificación de la ubicacion
     */
    public void eliminarUbicacion(Long id) {
        Ubicacion ubicacion = ubicacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la ubicacion con el id " + id));

        ubicacionRepository.delete(ubicacion);
    }

    /**
     * Convertir ubicacion a ubicacionDTO
     * @param ubicacion Entidad ubicacion
     * @return DTO de ubicacion
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
