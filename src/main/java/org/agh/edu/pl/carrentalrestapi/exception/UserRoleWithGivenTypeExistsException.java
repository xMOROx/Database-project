package org.agh.edu.pl.carrentalrestapi.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(reason = "User role with given type exists", value = CONFLICT)
public class UserRoleWithGivenTypeExistsException extends RuntimeException {
    public UserRoleWithGivenTypeExistsException(String type) {
        super("User role with type: " + type + " exists");
    }
}
