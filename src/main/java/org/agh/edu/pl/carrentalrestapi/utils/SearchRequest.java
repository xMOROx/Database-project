package org.agh.edu.pl.carrentalrestapi.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(value = PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class SearchRequest implements Serializable {
    @Serial
    private final static long serialVersionUID = 84561564984651654L;
    private List<FilterRequest> filters;
    private List<SortRequest> sorts;
    private Integer page;
    private Integer size;

    public List<FilterRequest> getFilters() {
        if (filters == null) {
            return List.of();
        }
        return filters;
    }

    public List<SortRequest> getSorts() {
        if (filters == null) {
            return List.of();
        }
        return sorts;
    }


}
