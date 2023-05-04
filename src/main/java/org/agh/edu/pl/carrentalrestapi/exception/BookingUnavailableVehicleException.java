package org.agh.edu.pl.carrentalrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Location not found", value = HttpStatus.BAD_REQUEST)
public class BookingUnavailableVehicleException extends RuntimeException {
    public BookingUnavailableVehicleException(String id) {
        super("Vehicle with given id: " + id + "is already booked in this time period");
    }
}
