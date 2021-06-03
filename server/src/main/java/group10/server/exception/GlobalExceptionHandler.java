package group10.server.exception;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Global Exception Handler that is responsible of handling various types of exceptions throughout the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Static Logger instance for logging purposes.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handler for ArihmeticException
     * @param request Incoming HttpServletRequest
     * @param t Exception object
     * @return Internal Server Error
     */
    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<?> handleArithmeticException(HttpServletRequest request, ArithmeticException t) {
        LOGGER.info("Caught exception: " + t.getMessage());
        Map<String, String> map = new HashMap<>();
        map.put("error", "Arithmetic error occurred");
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handler for IllegalArgumentException
     * @param request Incoming HttpServletRequest
     * @param t Exception object
     * @return Internal Server Error
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException t) {
        LOGGER.info("Caught exception: " + t.getMessage());
        Map<String, String> map = new HashMap<>();
        map.put("error", t.getMessage());
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handler for MethodArgumentNotValidException
     * @param request Incoming HttpServletRequest
     * @param t Exception object
     * @return Internal Server Error
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleIMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException t) {
        LOGGER.info("Caught exception: " + t.getMessage());
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

    /**
     * Handler for DataIntegrityViolationException
     * @param request Incoming HttpServletRequest
     * @param t Exception object
     * @return Internal Server Error
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleSQLException(HttpServletRequest request, DataIntegrityViolationException t) {
        LOGGER.info("Caught exception: " + t.getMessage());
        Map<String, String> map = new HashMap<>();
        map.put("error", "A user with the given username / e-mail exists");
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handler for Throwable
     * @param request Incoming HttpServletRequest
     * @param t Exception object
     * @return Internal Server Error
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handleRuntimeException(HttpServletRequest request, Throwable t) {
        LOGGER.info("Caught exception: " + t.getMessage());
        Map<String, String> map = new HashMap<>();
        map.put("error", "Runtime error occurred");
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
