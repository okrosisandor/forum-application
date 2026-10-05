package com.app.forum.exception;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RoleNotInitializedException.class)
    public ResponseEntity<ExceptionResponse> handleRoleNotInitializedException(RoleNotInitializedException ex) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
        		.message(ex.getMessage())
        		.details("User role was not initialized.")
        		.timestamp(LocalDateTime.now())
        		.build();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException ex) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message("Invalid username or password")
                .details("Please check your credentials and try again.")
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException ex){
		Set<String> errors = new HashSet<>();
		ex.getBindingResult().getAllErrors()
			.forEach(error -> {
				var errorMessage = error.getDefaultMessage();
				errors.add(errorMessage);
			});
		
		ExceptionResponse exceptionResponse = ExceptionResponse.builder()
				.validationErrors(errors)
				.build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}
    
    @ExceptionHandler(InvalidRecipientException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidRecipientException(InvalidRecipientException ex) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(ex.getMessage())
                .details("Invalid recipient specified.")
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(ex.getMessage())
                .details("The requested entity was not found.")
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = "A database constraint violation occurred.";

        String rootCauseMessage = NestedExceptionUtils.getMostSpecificCause(ex).getMessage();

        if (rootCauseMessage != null) {
            if (rootCauseMessage.contains("display_name")) {
                message = "Username is already taken. Please choose another.";
            } else if (rootCauseMessage.contains("email")) {
                message = "A user with this email already exists.";
            }
        }

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(message)
                .details("Database constraint violation.")
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGenericException(Exception ex) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
        		.message(ex.getMessage())
        		.details("An error occurred.")
        		.timestamp(LocalDateTime.now())
        		.build();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }
}
