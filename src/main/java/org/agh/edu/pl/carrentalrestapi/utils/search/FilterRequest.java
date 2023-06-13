package org.agh.edu.pl.carrentalrestapi.utils.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.agh.edu.pl.carrentalrestapi.utils.enums.FieldType;
import org.agh.edu.pl.carrentalrestapi.utils.enums.Operator;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(value = PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class FilterRequest implements Serializable {
    public final static long serialVersionUID = 7658765340895634809L;

    private String key;

    private Operator operator;

    private FieldType fieldType;

    private transient Object value;

    private transient Object valueTo;

    private transient List<Object> values;
}
