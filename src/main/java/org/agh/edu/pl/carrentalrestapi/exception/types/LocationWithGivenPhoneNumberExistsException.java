package org.agh.edu.pl.carrentalrestapi.exception.types;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(reason = "Location with given phone number already exists", value = CONFLICT)
public class LocationWithGivenPhoneNumberExistsException extends RuntimeException{
    public LocationWithGivenPhoneNumberExistsException(String email) {
        super("Location with phone number: " + email + " already exists");
    }
}
