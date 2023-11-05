package itacademy.s5t2.diceGame.exceptionLayer.business.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import itacademy.s5t2.diceGame.constants.CommonConstants;
import itacademy.s5t2.diceGame.exceptionLayer.business.request.DiceGameNotFoundException;
import itacademy.s5t2.diceGame.exceptionLayer.business.request.PlayerNotFoundException;
import itacademy.s5t2.diceGame.exceptionLayer.response.ApiError;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandlerGame extends ResponseEntityExceptionHandler{

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
    								HttpHeaders headers, 
    								HttpStatus status, 
    								WebRequest request) {
      
    	List<String> fieldErrors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(field -> field.getField() + ", " + field.getDefaultMessage())
            .collect(Collectors.toList());
        
        List<String> globalErrors = ex.getBindingResult()
            .getGlobalErrors()
            .stream()
            .map(field -> field.getObjectName() + ", " + field.getDefaultMessage())
            .collect(Collectors.toList());
      
        List<String> errors = new ArrayList<>();
        errors.addAll(globalErrors);
        errors.addAll(fieldErrors);
    
        ApiError err = new ApiError(LocalDateTime.now(),
        							CommonConstants.VALIDATION_ERROR,
        							errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(
    								Exception ex, 
    								WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ApiError err = new ApiError(LocalDateTime.now(), 
        							CommonConstants.CONSTRAINT_VIOLATION,
        							details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
    
    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(PlayerNotFoundException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ApiError err = new ApiError(LocalDateTime.now(), 
        							CommonConstants.PLAYER_NOT_FOUND,
        							details);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }
    
    @ExceptionHandler(DiceGameNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(DiceGameNotFoundException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ApiError err = new ApiError(LocalDateTime.now(), 
        							CommonConstants.GAME_NOT_FOUND,
        							details);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }
	
}
