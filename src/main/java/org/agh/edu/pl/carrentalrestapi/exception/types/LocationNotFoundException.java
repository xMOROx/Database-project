package org.agh.edu.pl.carrentalrestapi.exception.types;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Location not found", value = HttpStatus.NOT_FOUND)
public class LocationNotFoundException extends RuntimeException{
    public LocationNotFoundException(Long id) {
        super("Could not find location with id: " + id);
    }
    public LocationNotFoundException(String message) {
        super(message);
    }
}
