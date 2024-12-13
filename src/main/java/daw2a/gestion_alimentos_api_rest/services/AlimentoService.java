package daw2a.gestion_alimentos_api_rest.services;

import daw2a.gestion_alimentos_api_rest.dto.alimento.AlimentoDTO;
import daw2a.gestion_alimentos_api_rest.dto.alimento.CrearAlimentoDTO;
import daw2a.gestion_alimentos_api_rest.dto.alimento.ModificarAlimentoDTO;
import daw2a.gestion_alimentos_api_rest.entities.Alimento;
import daw2a.gestion_alimentos_api_rest.exceptions.RecursoNoEncontradoException;
import daw2a.gestion_alimentos_api_rest.repositories.AlimentoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AlimentoService {
    private final AlimentoRepository alimentoRepository;

    public AlimentoService(AlimentoRepository alimentoRepository) {
        this.alimentoRepository = alimentoRepository;
    }

    /**
     * Listar todos los alimentos o buscar por nombre
     * @param nombre Nombre del alimento
     * @param pageable Pageable
     * @return Listado de alimentos
     */
    public Page<?> listarAlimentos(String nombre, Pageable pageable) {
        Page<Alimento> alimentos;

        if (nombre != null && !nombre.isEmpty()) {
            alimentos = alimentoRepository.findByNombreContainingIgnoreCase(nombre, pageable);
        } else {
            alimentos = alimentoRepository.findAll(pageable);
        }

        return alimentos.map(this::convertirAAlimentoDTO);
    }

    /**
     * Obtener los detalles de un alimento identificado por su id
     * @param id Identificador del alimento
     * @return DTO con los datos del alimento
     * @throws RecursoNoEncontradoException Si no hay un alimento con ese id
     */
    public AlimentoDTO obtenerAlimento(Long id) {
        Alimento alimento = alimentoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Alimento con id " + id + " no encontrado"));

        return convertirAAlimentoDTO(alimento);
    }

    /**
     * Crear un nuevo alimento
     * @param crearAlimentoDTO DTO de alimento con los datos necesarios
     * @return AlimentoDTO
     */
    public AlimentoDTO crearAlimento(CrearAlimentoDTO crearAlimentoDTO) {
        Alimento alimento = new Alimento();
        alimento.setNombre(crearAlimentoDTO.getNombre());
        alimento.setTipo(crearAlimentoDTO.getTipo());
        alimento.setEstado("Cerrado");
        alimento.setFechaCaducidad(crearAlimentoDTO.getFechaCaducidad());

        return convertirAAlimentoDTO(alimentoRepository.save(alimento));
    }

    /**
     * Actualizar los datos de un alimento
     * @param id Identificador del alimento
     * @param modificarAlimentoDTO DTO con los datos para modificarlo
     * @return Alimento DTO
     * @throws RecursoNoEncontradoException Si no hay un alimento con ese id
     */
    public AlimentoDTO actualizarAlimento(Long id, ModificarAlimentoDTO modificarAlimentoDTO) {
        Alimento alimento = alimentoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Alimento con id " + id + " no encontrado"));

        if (modificarAlimentoDTO.getNombre() != null) {
            alimento.setNombre(modificarAlimentoDTO.getNombre());
        }
        if (modificarAlimentoDTO.getTipo() != null) {
            alimento.setTipo(modificarAlimentoDTO.getTipo());
        }
        if (modificarAlimentoDTO.getEstado() != null) {
            alimento.setEstado(modificarAlimentoDTO.getEstado());
        }
        if (modificarAlimentoDTO.getFechaCaducidad() != null) {
            alimento.setFechaCaducidad(modificarAlimentoDTO.getFechaCaducidad());
        }

        return convertirAAlimentoDTO(alimentoRepository.save(alimento));
    }

    /**
     * Eliminar un alimento
     * @param id Identificador del alimento
     * @throws RecursoNoEncontradoException Si no hay un alimento con ese id
     */
    public void eliminarAlimento(Long id) {
        Alimento alimento = alimentoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Alimento con id " + id + " no encontrado"));

        alimentoRepository.delete(alimento);
    }

    /**
     * Convertir una entidad en DTO
     * @param alimento Entidad de alimento
     * @return DTO de alimento
     */
    private AlimentoDTO convertirAAlimentoDTO(Alimento alimento) {
        AlimentoDTO alimentoDTO = new AlimentoDTO();
        alimentoDTO.setId(alimento.getId());
        alimentoDTO.setNombre(alimento.getNombre());
        alimentoDTO.setTipo(alimento.getTipo());
        alimentoDTO.setEstado(alimento.getEstado());
        alimentoDTO.setFechaCaducidad(alimento.getFechaCaducidad());
        return alimentoDTO;
    }
}
