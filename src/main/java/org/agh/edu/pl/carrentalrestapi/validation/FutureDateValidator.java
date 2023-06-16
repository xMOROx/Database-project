package org.agh.edu.pl.carrentalrestapi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class FutureDateValidator implements ConstraintValidator<FutureDate, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty()) return true;

        try {
            LocalDateTime date = LocalDateTime.parse(convertDate(value));
            return date.isAfter(LocalDateTime.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public void initialize(FutureDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    private String convertDate(String date) {
        if(date.matches("^[1-9][0-9]{3}-[0-9]{2}-[0-9]{2}$")) return date.trim() + "T00:00:00";

        return date.trim().replace(" ", "T");
    }
}
