package daw2a.gestion_alimentos_api_rest.exceptions;

/**
 * Excepción personalizada que se lanza cuando una ubicación está llena y no puede aceptar más elementos.
 * Puede usarse en escenarios donde se intenta agregar un elemento a una ubicación que ya ha alcanzado su capacidad máxima.
 * Extiende de {@link RuntimeException}.
 */
public class UbicacionLlenaException extends RuntimeException {

    /**
     * Constructor que crea una nueva instancia de la excepción con un mensaje específico.
     *
     * @param message El mensaje de error que describe la causa de la excepción.
     */
    public UbicacionLlenaException(String message) {
        super(message);
    }
}
