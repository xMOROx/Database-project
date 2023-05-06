package org.agh.edu.pl.carrentalrestapi.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.UpperCamelCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.agh.edu.pl.carrentalrestapi.utils.enums.SortDirection;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(value = UpperCamelCaseStrategy.class)
public class SortRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 4235345546456456L;
    private String key;
    private SortDirection direction;
}
