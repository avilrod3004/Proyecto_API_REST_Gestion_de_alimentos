package daw2a.gestion_alimentos_api_rest.exceptions;

/**
 * Excepción personalizada que se lanza cuando no se encuentra un recurso en el sistema.
 * Puede usarse, por ejemplo, cuando un recurso buscado no existe en la base de datos.
 * Extiende de {@link RuntimeException}.
 */
public class RecursoNoEncontradoException extends RuntimeException {

    /**
     * Constructor que crea una nueva instancia de la excepción con un mensaje específico.
     *
     * @param message El mensaje de error que describe la causa de la excepción.
     */
    public RecursoNoEncontradoException(String message) {
        super(message);
    }
}
