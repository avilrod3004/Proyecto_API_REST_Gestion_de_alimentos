package daw2a.gestion_alimentos_api_rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptioHandler {

    /**
     * Manejador para errores de validación
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();

        // Extraer mensajes de error para cada campo inválido
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(campo, mensaje);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

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

    /**
     * Manejador para cuando al crear una existencia, la ubicacion esta llena
     * @param ex
     * @return
     */
    @ExceptionHandler(UbicacionLlenaException.class)
    public ResponseEntity<String> handleUbicacionLlenaException(UbicacionLlenaException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    /**
     * Manejador para errores de elemento no encontrado no such element
     * @param ex
     * @return
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Manejador para errores de argumento ilegal
     * @param ex
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Manejador para errores de excepción general
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
