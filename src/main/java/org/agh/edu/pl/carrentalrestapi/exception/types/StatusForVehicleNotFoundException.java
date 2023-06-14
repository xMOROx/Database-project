package org.agh.edu.pl.carrentalrestapi.exception.types;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(reason = "Status for vehicle not found", value = NOT_FOUND)
public class StatusForVehicleNotFoundException extends RuntimeException {
    public StatusForVehicleNotFoundException(Long id) {
        super("Status for vehicle with id: " + id + " not found");
    }

    public StatusForVehicleNotFoundException(String status) {
        super("Status for vehicle with status: " + status + " code not found");
    }
}
