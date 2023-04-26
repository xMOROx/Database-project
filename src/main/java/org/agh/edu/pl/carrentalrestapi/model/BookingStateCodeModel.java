package org.agh.edu.pl.carrentalrestapi.model;

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
public class BookingStateCodeModel extends RepresentationModel<BookingStateCodeModel> {
    private Long id;
    private String bookingCode;
    private String description;
}
