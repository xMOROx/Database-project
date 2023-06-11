package org.agh.edu.pl.carrentalrestapi.exception.types;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.processing.Generated;

@ResponseStatus(reason = "Parameter has been set before", value = HttpStatus.CONFLICT)
public class ParameterNotNullException extends RuntimeException{
    public ParameterNotNullException(String parameterName) {
        super("Parameter: " + parameterName + " has been set before");
    }
}
