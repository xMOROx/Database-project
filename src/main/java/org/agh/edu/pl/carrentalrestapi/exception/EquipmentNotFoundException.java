package org.agh.edu.pl.carrentalrestapi.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import  org.springframework.http.HttpStatus;

@ResponseStatus(reason = "Equipment not found", value = HttpStatus.NOT_FOUND)
public class EquipmentNotFoundException extends RuntimeException {
    public EquipmentNotFoundException(Long id) {
        super("Could not find equipment with id: " + id);
    }

    public EquipmentNotFoundException(String code) {
        super("Could not find equipment with code: " + code);
    }
}
