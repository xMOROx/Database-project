package org.agh.edu.pl.carrentalrestapi.exception.types;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(reason = "Booking not found", value = NOT_FOUND)
public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException(Long id) {
        super("Booking with id: " + id + " not found");
    }
}
