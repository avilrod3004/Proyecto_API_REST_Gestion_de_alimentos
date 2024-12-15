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

import java.time.LocalDate;


/**
 * Servicio para gestionar las operaciones relacionadas con los alimentos.
 * <p>Este servicio permite realizar las operaciones de listar, obtener, crear, actualizar y eliminar alimentos,
 * así como buscar alimentos por nombre y fecha de caducidad.</p>
 */
@Service
public class AlimentoService {
    private final AlimentoRepository alimentoRepository;

    /**
     * Constructor del servicio de alimentos.
     *
     * @param alimentoRepository Repositorio de alimentos utilizado para realizar las operaciones de persistencia.
     */
    public AlimentoService(AlimentoRepository alimentoRepository) {
        this.alimentoRepository = alimentoRepository;
    }

    /**
     * Lista todos los alimentos o busca por nombre.
     * <p>Si se proporciona un nombre, se realiza una búsqueda de alimentos cuyo nombre contenga la cadena proporcionada.
     * Si no se proporciona nombre, se listan todos los alimentos.</p>
     *
     * @param nombre Nombre del alimento (opcional).
     * @param pageable Configuración de paginación.
     * @return Una página de alimentos.
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
     * Lista los alimentos cuya fecha de caducidad esté dentro de un rango.
     * <p>Permite listar alimentos cuya fecha de caducidad se encuentre entre dos fechas específicas.</p>
     *
     * @param fechaInicio Fecha de inicio del rango.
     * @param fechaFin Fecha de fin del rango.
     * @param pageable Configuración de paginación.
     * @return Una página de alimentos.
     */
    public Page<AlimentoDTO> listarEntreFechasCaducidad(LocalDate fechaInicio, LocalDate fechaFin, Pageable pageable) {
        Page<Alimento> alimentos = alimentoRepository.findByFechaCaducidadBetween(fechaInicio, fechaFin, pageable);
        return alimentos.map(this::convertirAAlimentoDTO);
    }

    /**
     * Obtiene los detalles de un alimento identificado por su id.
     * <p>Busca un alimento por su identificador y retorna sus detalles en forma de un DTO.</p>
     *
     * @param id Identificador del alimento.
     * @return El DTO del alimento encontrado.
     * @throws RecursoNoEncontradoException Si no se encuentra un alimento con el id proporcionado.
     */
    public AlimentoDTO obtenerAlimento(Long id) {
        Alimento alimento = alimentoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Alimento con id " + id + " no encontrado"));

        return convertirAAlimentoDTO(alimento);
    }

    /**
     * Crea un nuevo alimento.
     * <p>Permite crear un alimento con los datos proporcionados en el DTO de creación.</p>
     *
     * @param crearAlimentoDTO DTO con los datos del nuevo alimento.
     * @return El DTO del alimento creado.
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
     * Actualiza los datos de un alimento.
     * <p>Permite actualizar un alimento existente con los datos proporcionados en el DTO de modificación.</p>
     *
     * @param id Identificador del alimento a modificar.
     * @param modificarAlimentoDTO DTO con los datos a actualizar.
     * @return El DTO del alimento actualizado.
     * @throws RecursoNoEncontradoException Si no se encuentra un alimento con el id proporcionado.
     */
    public AlimentoDTO actualizarAlimento(Long id, ModificarAlimentoDTO modificarAlimentoDTO) {
        Alimento alimento = alimentoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Alimento con id " + id + " no encontrado"));

        if (modificarAlimentoDTO.getNombre() != null && !modificarAlimentoDTO.getNombre().isEmpty()) {
            alimento.setNombre(modificarAlimentoDTO.getNombre());
        }
        if (modificarAlimentoDTO.getTipo() != null && !modificarAlimentoDTO.getTipo().isEmpty()) {
            alimento.setTipo(modificarAlimentoDTO.getTipo());
        }
        if (modificarAlimentoDTO.getEstado() != null && !modificarAlimentoDTO.getEstado().isEmpty()) {
            alimento.setEstado(modificarAlimentoDTO.getEstado());
        }
        if (modificarAlimentoDTO.getFechaCaducidad() != null) {
            alimento.setFechaCaducidad(modificarAlimentoDTO.getFechaCaducidad());
        }

        return convertirAAlimentoDTO(alimentoRepository.save(alimento));
    }

    /**
     * Elimina un alimento.
     * <p>Permite eliminar un alimento de la base de datos mediante su id.</p>
     *
     * @param id Identificador del alimento a eliminar.
     * @throws RecursoNoEncontradoException Si no se encuentra un alimento con el id proporcionado.
     */
    public void eliminarAlimento(Long id) {
        Alimento alimento = alimentoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Alimento con id " + id + " no encontrado"));

        alimentoRepository.delete(alimento);
    }

    /**
     * Convierte una entidad de alimento en un DTO de alimento.
     * <p>Este método se utiliza para transformar un objeto de entidad {@link Alimento} en su representación DTO {@link AlimentoDTO}.</p>
     *
     * @param alimento La entidad de alimento que se desea convertir.
     * @return El DTO de alimento correspondiente.
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
