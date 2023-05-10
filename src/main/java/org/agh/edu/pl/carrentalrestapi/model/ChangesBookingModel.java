package org.agh.edu.pl.carrentalrestapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
public class ChangesBookingModel extends RepresentationModel<ChangesBookingModel> {
    private Long id;
    private Long bookingId;
    private String whoChange;
    private String changeDate;
}
