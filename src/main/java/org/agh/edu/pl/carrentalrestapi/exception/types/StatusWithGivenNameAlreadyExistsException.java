package org.agh.edu.pl.carrentalrestapi.exception.types;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(reason = "Status with given name already exists", value = CONFLICT)
public class StatusWithGivenNameAlreadyExistsException  extends RuntimeException{
    public StatusWithGivenNameAlreadyExistsException(String name) {
        super("Status with code: " + name + " already exists");
    }
}
