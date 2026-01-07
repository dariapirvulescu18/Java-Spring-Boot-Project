package dpirvulescu.hotelManagement.aop;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorControllerAdvice {


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "error", e.getMessage(),
                        "status", HttpStatus.BAD_REQUEST.value(),
                        "timestamp", LocalDateTime.now()
                ));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalState(IllegalStateException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "error", e.getMessage(),
                        "status", HttpStatus.CONFLICT.value(),
                        "timestamp", LocalDateTime.now()
                ));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(EntityNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "error", e.getMessage(),
                        "status", HttpStatus.NOT_FOUND.value(),
                        "timestamp", LocalDateTime.now()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();

        body.put("error", errorMessage);
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.badRequest().body(body);
    }
}
