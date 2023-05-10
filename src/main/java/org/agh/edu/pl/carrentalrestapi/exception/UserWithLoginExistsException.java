package org.agh.edu.pl.carrentalrestapi.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(reason = "User with this login already exists", value = CONFLICT, code = CONFLICT)
public class UserWithLoginExistsException extends RuntimeException {
    public UserWithLoginExistsException(String login) {
        super("User with login: " + login + " already exists");
    }
}
