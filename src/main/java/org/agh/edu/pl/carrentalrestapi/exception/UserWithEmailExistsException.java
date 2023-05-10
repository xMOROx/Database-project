package org.agh.edu.pl.carrentalrestapi.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(reason = "User with this email already exists", value = CONFLICT, code = CONFLICT)
public class UserWithEmailExistsException extends RuntimeException {
    public UserWithEmailExistsException(String email) {
        super("Vehicle with registration: " + email + " already exists");
    }
}
