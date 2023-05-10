package org.agh.edu.pl.carrentalrestapi.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(value = PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class PageableRequest implements Serializable {
    public final static long serialVersionUID = 534095743905347L;

    private Integer page;

    private Integer size;
    //TODO: add sorting
    public static Pageable toPageable(PageableRequest pageableRequest) {
        if (Objects.isNull(pageableRequest)) {
            return Pageable.unpaged();
        }

        return PageRequest.of(
                Objects.requireNonNullElse(pageableRequest.getPage(),0),
                Objects.requireNonNullElse(pageableRequest.getSize(), 100)
        );
    }

    public static PageableRequest of(Integer page, Integer size) {
        return PageableRequest.builder()
                .page(page)
                .size(size)
                .build();
    }
}
