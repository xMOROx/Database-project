package org.agh.edu.pl.carrentalrestapi.exception.types;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(reason = "Equipment with given code exists", value = CONFLICT)
public class EquipmentWithGivenCodeExistsException extends RuntimeException {
    public EquipmentWithGivenCodeExistsException(String code) {
        super("Equipment with code: " + code + " exists");
    }
}
