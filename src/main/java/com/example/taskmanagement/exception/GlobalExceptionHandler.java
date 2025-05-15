package com.example.taskmanagement.exception;

import com.example.taskmanagement.DTO.ErrorResponse;
import com.example.taskmanagement.exception.types.ResourceNotFoundException;
import com.example.taskmanagement.exception.types.UnauthorizedAccessException;
import com.example.taskmanagement.exception.types.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A global exception handler class for handling exceptions in the application.
 * This class uses Spring's ControllerAdvice annotation to make it global.
 * It also uses Lombok's Slf4j annotation for logging.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handles ResourceNotFoundException and provides a custom error response.
     *
     * @param ex The ResourceNotFoundException that occurred.
     * @param request The HttpServletRequest object representing the current request.
     * @return A ResponseEntity containing the custom error response and a HTTP status code of 404 (Not Found).
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ) {
        log.warn("Resource not found: {}", ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.createErrorResponse(
                HttpStatus.NOT_FOUND,
                "Resource Not Found",
                ex.getMessage(),
                request
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles ValidationException and provides a custom error response.
     *
     * @param ex The ValidationException that occurred.
     * @param request The HttpServletRequest object representing the current request.
     * @return A ResponseEntity containing the custom error response and a HTTP status code of 400 (Bad Request).
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            ValidationException ex,
            HttpServletRequest request
    ) {
        log.error("Validation error: {}", ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.createErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation Failed",
                ex.getMessage(),
                request
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles MethodArgumentNotValidException and provides a custom error response with detailed field errors.
     *
     * @param ex The MethodArgumentNotValidException that occurred.
     * @param request The HttpServletRequest object representing the current request.
     * @return A ResponseEntity containing the custom error response and a HTTP status code of 400 (Bad Request).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        log.error("Validation errors: {}", errors);

        ErrorResponse errorResponse = ErrorResponse.createValidationErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation Failed",
                errors,
                request
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles DataIntegrityViolationException and provides a custom error response.
     *
     * @param ex The DataIntegrityViolationException that occurred.
     * @param request The HttpServletRequest object representing the current request.
     * @return A ResponseEntity containing the custom error response and a HTTP status code of 409 (Conflict).
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException ex,
            HttpServletRequest request
    ) {
        log.error("Data integrity violation: {}", ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.createErrorResponse(
                HttpStatus.CONFLICT,
                "Data Conflict",
                "A data integrity violation occurred",
                request
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * Handles UnauthorizedAccessException and provides a custom error response.
     *
     * @param ex The UnauthorizedAccessException that occurred.
     * @param request The HttpServletRequest object representing the current request.
     * @return A ResponseEntity containing the custom error response and a HTTP status code of 403 (Forbidden).
     */
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedAccess(
            UnauthorizedAccessException ex,
            HttpServletRequest request
    ) {
        log.warn("Unauthorized access attempt: {}", ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.createErrorResponse(
                HttpStatus.FORBIDDEN,
                "Unauthorized",
                ex.getMessage(),
                request
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    /**
     * Handles any other exceptions and provides a generic error response.
     *
     * @param ex The Exception that occurred.
     * @param request The HttpServletRequest object representing the current request.
     * @return A ResponseEntity containing the generic error response and a HTTP status code of 500 (Internal Server Error).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request
    ) {
        log.error("Unexpected error occurred", ex);
        ErrorResponse errorResponse = ErrorResponse.createErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                "An unexpected error occurred",
                request
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
