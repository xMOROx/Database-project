package org.agh.edu.pl.carrentalrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Vehicle not found", value = HttpStatus.NOT_FOUND)
public class VehicleNotFoundException extends RuntimeException{
    public VehicleNotFoundException(String message) {
        super("Could not find vehicle with id: " + message);
    }
}
