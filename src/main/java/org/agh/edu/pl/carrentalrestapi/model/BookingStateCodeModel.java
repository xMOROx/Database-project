package org.agh.edu.pl.carrentalrestapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "ChangesBookings")
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "ChangesBookings")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingStateCodeModel extends RepresentationModel<BookingStateCodeModel> {
    private Long id;
    private String bookingCode;
    private String description;
}
