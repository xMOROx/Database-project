package org.agh.edu.pl.carrentalrestapi.exception.types;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(reason = "Vehicle with this registration already exists", value = CONFLICT, code = CONFLICT)
public class VehicleWithRegistrationExistsException extends RuntimeException {
    public VehicleWithRegistrationExistsException(String registration) {
        super("Vehicle with registration: " + registration + " already exists");
    }
}
