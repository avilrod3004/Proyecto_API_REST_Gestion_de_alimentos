package daw2a.gestion_alimentos_api_rest.exceptions;

/**
 * Excepción personalizada que se lanza cuando se intenta crear o realizar una operación
 * que genera un recurso duplicado en el sistema (por ejemplo, un email ya registrado).
 * Extiende de {@link RuntimeException}.
 */
public class RecursoDuplicadoException extends RuntimeException {

    /**
     * Constructor que crea una nueva instancia de la excepción con un mensaje específico.
     *
     * @param message El mensaje de error que describe la causa de la excepción.
     */
    public RecursoDuplicadoException(String message) {
        super(message);
    }
}
