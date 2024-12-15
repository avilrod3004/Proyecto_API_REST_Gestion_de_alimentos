package daw2a.gestion_alimentos_api_rest.services;

import daw2a.gestion_alimentos_api_rest.dto.existencia.*;
import daw2a.gestion_alimentos_api_rest.entities.Alimento;
import daw2a.gestion_alimentos_api_rest.entities.Existencia;
import daw2a.gestion_alimentos_api_rest.entities.Ubicacion;
import daw2a.gestion_alimentos_api_rest.exceptions.RecursoNoEncontradoException;
import daw2a.gestion_alimentos_api_rest.exceptions.UbicacionLlenaException;
import daw2a.gestion_alimentos_api_rest.repositories.AlimentoRepository;
import daw2a.gestion_alimentos_api_rest.repositories.ExistenciaRepository;
import daw2a.gestion_alimentos_api_rest.repositories.UbicacionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Servicio encargado de gestionar las operaciones relacionadas con las existencias de alimentos
 * en ubicaciones específicas dentro del sistema de gestión de alimentos.
 */
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
     * Lista las existencias de alimentos filtradas por id de alimento, id de ubicación o ambas.
     * Si no se proporciona filtro, devuelve todas las existencias.
     *
     * @param idAlimento Id del alimento a filtrar (opcional).
     * @param idUbicacion Id de la ubicación a filtrar (opcional).
     * @param pageable Objeto de paginación para controlar los resultados.
     * @return Un Page con los objetos ExistenciaDTO que representan las existencias.
     */
    public Page<ExistenciaDTO> listarExistencias(Long idAlimento, Long idUbicacion, Pageable pageable) {
        Page<Existencia> existencias;

        if (idAlimento != null && idUbicacion != null) {
            existencias = existenciaRepository.findByAlimento_IdAndUbicacion_Id(idAlimento, idUbicacion, pageable);
        } else if (idAlimento != null) {
            existencias = existenciaRepository.findByAlimento_Id(idAlimento, pageable);
        } else if (idUbicacion != null) {
            existencias = existenciaRepository.findByUbicacion_Id(idUbicacion, pageable);
        } else {
            existencias = existenciaRepository.findAll(pageable);
        }

        return existencias.map(this::convertirAExistenciaDTO);
    }

    /**
     * Lista las existencias que caducan dentro de un rango de fechas, agrupadas por su ubicación.
     *
     * @param size Número de elementos por página.
     * @param fechaInicio Fecha de inicio del rango.
     * @param fechaFin Fecha de fin del rango.
     * @return Un Page con los objetos ExistenciaDetallesDTO que representan las existencias y detalles adicionales.
     */
    public Page<ExistenciaDetallesDTO> listadoCaducanPorUbicacion(int size, LocalDate fechaInicio, LocalDate fechaFin) {
        int page = 0;

        Pageable pageable = PageRequest.of(page, size, Sort.by("ubicacion.tipoUbicacion").ascending());
        Page<Existencia> existencias = existenciaRepository.findByAlimento_FechaCaducidadBetween(fechaInicio, fechaFin, pageable);

        return existencias.map(this::convertirAExistenciaDetallesDTO);
    }

    /**
     * Consulta los detalles de una existencia de alimento en una ubicación específica.
     *
     * @param id Identificador de la existencia.
     * @return El DTO de la existencia.
     * @throws RecursoNoEncontradoException Si no se encuentra la existencia con el id proporcionado.
     */
    public ExistenciaDTO consultarExistencia(Long id) {
        Existencia existencia = existenciaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Existencia con el id " + id + " no encontrada"));

        return convertirAExistenciaDTO(existencia);
    }

    /**
     * Registra una nueva existencia de alimento en una ubicación específica.
     * Verifica si la ubicación tiene suficiente capacidad antes de permitir la creación de la existencia.
     *
     * @param nuevaExistencia DTO con los datos necesarios para crear la nueva existencia.
     * @return El DTO de la nueva existencia creada.
     * @throws RecursoNoEncontradoException Si no se encuentra el alimento o la ubicación.
     * @throws UbicacionLlenaException Si la ubicación está llena y no tiene capacidad suficiente.
     */
    @Transactional
    public ExistenciaDTO crearExistencia(CrearExistenciaDTO nuevaExistencia) {
        Alimento alimento = alimentoRepository.findById(nuevaExistencia.getIdAlimento())
                .orElseThrow(() -> new RecursoNoEncontradoException("Alimento con el id " + nuevaExistencia.getIdAlimento() + " no encontrado"));

        Ubicacion ubicacion = ubicacionRepository.findById(nuevaExistencia.getIdUbicacion())
                .orElseThrow(() -> new RecursoNoEncontradoException("Ubicacion con id " + nuevaExistencia.getIdUbicacion() + " no encontrada"));


        Long capacidadUbicacion = ubicacion.getCapacidad();
        List<Existencia> existenciasMismaUbicacion = existenciaRepository.findByUbicacion_Id(ubicacion.getId(), null).getContent();
        Long capacidadOcupada = existenciasMismaUbicacion.stream()
                .mapToLong(Existencia::getCantidad)
                .sum();

        if (capacidadOcupada + nuevaExistencia.getCantidad() > capacidadUbicacion) {
            throw new UbicacionLlenaException("La ubicacion con descripcion " + ubicacion.getDescripcion() + " esta llena");
        }

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
     * Actualiza la cantidad de una existencia existente.
     *
     * @param id Identificador de la existencia a actualizar.
     * @param modificarExistenciaDTO DTO con el dato de cantidad a modificar.
     * @return El DTO con los datos de la existencia actualizada.
     * @throws RecursoNoEncontradoException Si no se encuentra la existencia con el id proporcionado.
     */
    @Transactional
    public ExistenciaDTO actualizarCantidad(Long id, ModificarExistenciaDTO modificarExistenciaDTO) {
        Existencia existencia = existenciaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Existencia con el id " + id + " no encontrada"));

        existencia.setCantidad(modificarExistenciaDTO.getCantidad());
        return convertirAExistenciaDTO(existenciaRepository.save(existencia));
    }

    /**
     * Mueve una existencia de un alimento de una ubicación a otra.
     *
     * @param id Identificador de la existencia a mover.
     * @param moverExistenciaDTO DTO con el id de la nueva ubicación.
     * @return El DTO de la existencia con la nueva ubicación.
     * @throws RecursoNoEncontradoException Si no se encuentra la existencia o la ubicación con los ids proporcionados.
     */
    @Transactional
    public ExistenciaDTO moverExistencia(Long id, MoverExistenciaDTO moverExistenciaDTO) {
        Existencia existencia = existenciaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Existencia con el id " + id + " no encontrada"));

        Ubicacion ubicacion = ubicacionRepository.findById(moverExistenciaDTO.getIdUbicacion())
                .orElseThrow(() -> new RecursoNoEncontradoException("Ubicacion con el id " + id + " no encontrada"));

        existencia.setUbicacion(ubicacion);
        return convertirAExistenciaDTO(existenciaRepository.save(existencia));
    }

    /**
     * Consume una cantidad específica de un alimento en una ubicación, reduciendo la cantidad
     * de la existencia más antigua.
     *
     * @param idAlimento Id del alimento que se va a consumir.
     * @param idUbicacion Id de la ubicación donde se encuentra el alimento.
     * @param cantidad Cantidad a consumir.
     * @return El DTO de la existencia actualizada.
     * @throws RuntimeException Si no hay suficiente cantidad o no hay existencias disponibles.
     */
    @Transactional
    public ExistenciaDTO consumirExistencia(Long idAlimento, Long idUbicacion, Long cantidad) {
        // Obtener las existencias del alimento en la ubicación, ordenadas por fecha de entrada (ascendente)
        Page<Existencia> existenciasPage = existenciaRepository.findByAlimento_IdAndUbicacion_IdOrderByFechaEntradaAsc(idAlimento, idUbicacion, PageRequest.of(0, 1));

        if (existenciasPage.isEmpty()) {
            throw new RuntimeException("No hay existencias disponibles para este alimento en la ubicación.");
        }

        // Obtener la existencia más antigua (primer elemento de la página)
        Existencia existencia = existenciasPage.getContent().get(0);

        if (existencia.getCantidad() < cantidad) {
            throw new RuntimeException("No hay suficiente cantidad en la existencia más antigua.");
        }

        existencia.setCantidad(existencia.getCantidad() - cantidad);

        if (existencia.getCantidad() == 0) {
            existenciaRepository.delete(existencia);
            return convertirAExistenciaDTO(existencia);
        } else {
            return convertirAExistenciaDTO(existenciaRepository.save(existencia));
        }
    }

    /**
     * Elimina una existencia de alimento en una ubicación específica.
     *
     * @param id Identificador de la existencia a eliminar.
     * @throws RecursoNoEncontradoException Si no se encuentra la existencia con el id proporcionado.
     */
    public void eliminarExistencia(Long id) {
        Existencia existencia = existenciaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Existencia con el id " + id + " no encontrada"));

        existenciaRepository.delete(existencia);
    }

    /**
     * Convierte una entidad Existencia a su correspondiente DTO ExistenciaDTO.
     *
     * @param existencia La entidad Existencia a convertir.
     * @return El DTO ExistenciaDTO.
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

    /**
     * Convierte una entidad Existencia a su correspondiente DTO ExistenciaDetallesDTO,
     * añadiendo detalles adicionales sobre la existencia.
     *
     * @param existencia La entidad Existencia a convertir.
     * @return El DTO ExistenciaDetallesDTO.
     */
    private ExistenciaDetallesDTO convertirAExistenciaDetallesDTO(Existencia existencia) {
        ExistenciaDetallesDTO existenciaDetallesDTO = new ExistenciaDetallesDTO();

        existenciaDetallesDTO.setId(existencia.getId());

        existenciaDetallesDTO.setIdAlimento(existencia.getAlimento().getId());
        existenciaDetallesDTO.setNombreAlimento(existencia.getAlimento().getNombre());
        existenciaDetallesDTO.setFechaCaducidad(existencia.getAlimento().getFechaCaducidad());

        existenciaDetallesDTO.setIdUbicacion(existencia.getUbicacion().getId());
        existenciaDetallesDTO.setDescripcionUbicacion(existencia.getUbicacion().getDescripcion());
        existenciaDetallesDTO.setTipoUbicacion(existencia.getUbicacion().getTipoUbicacion());

        existenciaDetallesDTO.setCantidad(existencia.getCantidad());
        existenciaDetallesDTO.setFechaEntrada(existencia.getFechaEntrada());

        return existenciaDetallesDTO;
    }
}
