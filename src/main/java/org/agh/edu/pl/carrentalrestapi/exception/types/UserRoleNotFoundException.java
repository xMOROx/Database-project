package org.agh.edu.pl.carrentalrestapi.exception.types;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(reason = "User role with given type not found", value = NOT_FOUND)
public class UserRoleNotFoundException extends RuntimeException {
    public UserRoleNotFoundException(String type) {
        super("User role with type: " + type + " not found");
    }

    public UserRoleNotFoundException(Long id) {
        super("User role with id: " + id + " not found");
    }
}
