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

/**
 * Manejador global de excepciones que se encarga de capturar y gestionar diversas excepciones lanzadas
 * en la aplicación. Los métodos de esta clase proporcionan respuestas adecuadas para los diferentes tipos
 * de errores que pueden ocurrir durante el ciclo de vida de las solicitudes HTTP.
 */
//@ControllerAdvice
public class GlobalExceptioHandler {

    /**
     * Manejador para errores de validación cuando los datos de entrada no cumplen con las restricciones de los objetos.
     * @param ex Excepción lanzada cuando los datos no son válidos
     * @return Respuesta con los errores de validación por campo, en formato JSON
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
     * Manejador para errores de recursos no encontrados.
     * @param ex Excepción lanzada cuando un recurso no se encuentra
     * @return Respuesta con el mensaje de error, con código HTTP 404 (Not Found)
     */
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<String> handleRecursoNoEncontradoException(RecursoNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Manejador para errores de recursos duplicados.
     * @param ex Excepción lanzada cuando se intenta crear un recurso duplicado
     * @return Respuesta con el mensaje de error, con código HTTP 409 (Conflict)
     */
    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<String> handleRecursoDuplicadoException(RecursoDuplicadoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    /**
     * Manejador para errores de ubicación llena.
     * @param ex Excepción lanzada cuando una ubicación está llena
     * @return Respuesta con el mensaje de error, con código HTTP 409 (Conflict)
     */
    @ExceptionHandler(UbicacionLlenaException.class)
    public ResponseEntity<String> handleUbicacionLlenaException(UbicacionLlenaException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    /**
     * Manejador para errores de elementos no encontrados (como en el caso de NoSuchElementException).
     * @param ex Excepción lanzada cuando no se encuentra el elemento solicitado
     * @return Respuesta con el mensaje de error, con código HTTP 404 (Not Found)
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Manejador para errores de argumentos ilegales.
     * @param ex Excepción lanzada cuando un argumento proporcionado no es válido
     * @return Respuesta con el mensaje de error, con código HTTP 400 (Bad Request)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Manejador para errores generales no especificados.
     * @param ex Excepción general que no ha sido manejada específicamente por otros métodos
     * @return Respuesta con el mensaje de error, con código HTTP 500 (Internal Server Error)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
