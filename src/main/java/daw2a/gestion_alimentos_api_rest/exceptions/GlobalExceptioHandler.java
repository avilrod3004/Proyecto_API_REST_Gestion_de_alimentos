package daw2a.gestion_alimentos_api_rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptioHandler {

    /**
     * Manejador para errores de recursos no encontrados
     * @param ex Excepción
     * @return Mensaje de errror
     */
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<String> handleRecursoNoEncontradoException(RecursoNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Manejador para errores de recurso duplicado
     * @param ex Excepcion
     * @return
     */
    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<String> handleRecursoDuplicadoException(RecursoDuplicadoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
