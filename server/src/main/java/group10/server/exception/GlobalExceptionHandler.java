package group10.server.exception;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<?> handleArithmeticException(HttpServletRequest request, ArithmeticException t) {
        LOGGER.error(t.getMessage(), t);
        Map<String, String> map = new HashMap<>();
        map.put("error", "Arithmetic error occurred");
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException t) {
        LOGGER.error(t.getMessage(), t);
        Map<String, String> map = new HashMap<>();
        map.put("error", t.getMessage());
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleIMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException t) {
        LOGGER.error(t.getMessage(), t);
        BindingResult result = t.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        Map<String, String> map = new HashMap<>();
        if (fieldErrors.isEmpty()) {
            map.put("error", "Exception occurred");
        } else {
            for (FieldError f : fieldErrors) {
                map.put("error", f.getDefaultMessage());
            }
        }
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleSQLException(HttpServletRequest request, DataIntegrityViolationException t) {
        LOGGER.error(t.getMessage(), t);
        Map<String, String> map = new HashMap<>();
        map.put("error", "Key violation");
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handleRuntimeException(HttpServletRequest request, Throwable t) {
        LOGGER.error(t.getMessage(), t);
        Map<String, String> map = new HashMap<>();
        map.put("error", "Runtime error occurred");
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
