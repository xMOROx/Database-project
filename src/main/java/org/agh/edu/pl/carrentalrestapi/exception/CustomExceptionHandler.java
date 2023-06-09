package org.agh.edu.pl.carrentalrestapi.exception;

import jakarta.validation.constraints.NotNull;
import org.agh.edu.pl.carrentalrestapi.exception.types.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
        messages.put("message", ex.getMessage());

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
            VehicleParametersNotFoundException.class,
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
            VehicleWithRegistrationExistsException.class
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
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        ErrorDetails errorDetails = new ErrorDetails(
                errors,
                request.getDescription(false),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}