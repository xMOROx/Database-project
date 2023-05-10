package org.agh.edu.pl.carrentalrestapi.exception.types;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(reason = "Vehicle parameters not found", value = NOT_FOUND)
public class VehicleParametersNotFoundException extends RuntimeException{
    public VehicleParametersNotFoundException(Long id) {
        super("Could not find vehicle parameters with id: " + id);
    }
}
