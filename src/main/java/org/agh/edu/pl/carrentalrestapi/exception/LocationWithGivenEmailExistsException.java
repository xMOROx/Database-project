package org.agh.edu.pl.carrentalrestapi.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(reason = "Location with given email already exists", value = CONFLICT)
public class LocationWithGivenEmailExistsException extends RuntimeException{
    public LocationWithGivenEmailExistsException(String email) {
        super("Location with email: " + email + " already exists");
    }
}
