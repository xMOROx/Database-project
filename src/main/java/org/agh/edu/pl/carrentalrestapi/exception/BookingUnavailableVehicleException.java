package org.agh.edu.pl.carrentalrestapi.exception;

public class BookingUnavailableVehicleException extends RuntimeException {
    public BookingUnavailableVehicleException(String message) {
        super(message);
    }
}
