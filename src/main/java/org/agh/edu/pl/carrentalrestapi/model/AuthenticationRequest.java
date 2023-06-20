package org.agh.edu.pl.carrentalrestapi.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class AuthenticationRequest {
    private String email;
    private String password;
}
