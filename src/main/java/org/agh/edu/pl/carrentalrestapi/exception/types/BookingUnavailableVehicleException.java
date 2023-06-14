package org.agh.edu.pl.carrentalrestapi.exception.types;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Location not found", value = HttpStatus.CONFLICT)
public class BookingUnavailableVehicleException extends RuntimeException {
    public BookingUnavailableVehicleException(Long id) {
        super("Vehicle with given id: " + id + " is already booked in this time period");
    }
}
