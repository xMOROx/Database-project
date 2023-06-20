package org.agh.edu.pl.carrentalrestapi.exception;

import jakarta.validation.constraints.NotNull;
import org.agh.edu.pl.carrentalrestapi.exception.types.*;
import org.agh.edu.pl.carrentalrestapi.utils.StringConvert;
import org.hibernate.query.SemanticException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllException(Exception ex, WebRequest request) {
        Map<String, String> messages = new HashMap<>();
        messages.put("message", "Internal server error");

        ErrorDetails errorDetails = new ErrorDetails(
                messages,
                request.getDescription(false),
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(
                errorDetails,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({
            JpaSystemException.class
    })
    public final ResponseEntity<ErrorDetails> handleJpaSystemException(JpaSystemException ex, WebRequest request) {
        Map<String, String> messages = new HashMap<>();
        messages.put("message", "Jpa system error");
        messages.put("hint", "Check if request body is valid");
        messages.put("exception", ex.getMessage());

        ErrorDetails errorDetails = new ErrorDetails(
                messages,
                request.getDescription(false),
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(
                errorDetails,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            VehicleNotFoundException.class,
            EquipmentNotFoundException.class,
            CommentNotFoundException.class,
            UserRoleNotFoundException.class,
            UserNotFoundException.class,
            LocationNotFoundException.class,
            BookingNotFoundException.class,
            StatusForVehicleNotFoundException.class,
    })
    public final ResponseEntity<ErrorDetails> handleNotFoundException(RuntimeException ex, WebRequest request) {
        Map<String, String> messages = new HashMap<>();
        messages.put("message", ex.getMessage());

        ErrorDetails errorDetails = new ErrorDetails(
                messages,
                request.getDescription(false),
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(
                errorDetails,
                HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler({
            BookingUnavailableVehicleException.class,
            EquipmentWithGivenCodeExistsException.class,
            LocationWithGivenEmailExistsException.class,
            LocationWithGivenPhoneNumberExistsException.class,
            UserRoleWithGivenTypeExistsException.class,
            VehicleWithRegistrationExistsException.class,
            ParameterNotNullException.class,
            StatusWithGivenNameAlreadyExistsException.class,
            UserWithEmailExistsException.class,
            BookingConflictException.class
    })
    public final ResponseEntity<ErrorDetails> handleConflictException(RuntimeException ex, WebRequest request) {
        Map<String, String> messages = new HashMap<>();
        messages.put("message", ex.getMessage());

        ErrorDetails errorDetails = new ErrorDetails(
                messages,
                request.getDescription(false),
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value());

        return new ResponseEntity<>(
                errorDetails,
                HttpStatus.CONFLICT);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(StringConvert.convertToCamelCase(error.getField()), error.getDefaultMessage()));
        ErrorDetails errorDetails = new ErrorDetails(
                errors,
                request.getDescription(false),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> messages = new HashMap<>();
        messages.put("message", "Invalid JSON request");
        messages.put("hint", "Check if request body is valid");
        messages.put("exception", ex.getMessage().split(":")[0].trim());

        ErrorDetails errorDetails = new ErrorDetails(
                messages,
                request.getDescription(false),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(
                errorDetails,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(
            BadCredentialsException.class
    )
    public final ResponseEntity<ErrorDetails> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        Map<String, String> messages = new HashMap<>();
        messages.put("message", "Bad credentials");
        messages.put("hint", "Check if email and password are correct");

        ErrorDetails errorDetails = new ErrorDetails(
                messages,
                request.getDescription(false),
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value());

        return new ResponseEntity<>(
                errorDetails,
                HttpStatus.UNAUTHORIZED);
    }
}
