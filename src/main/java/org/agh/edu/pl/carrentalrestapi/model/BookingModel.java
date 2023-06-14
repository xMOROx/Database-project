package org.agh.edu.pl.carrentalrestapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.agh.edu.pl.carrentalrestapi.entity.BookingStateCode;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "Bookings")
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "Bookings")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingModel extends RepresentationModel<BookingModel> {
    private Long id;
    private UserModel user;
    private VehicleModel vehicle;
    private LocationModel pickupLocation;
    private BookingStateCodeModel bookingStateCode;
    private LocalDateTime receiptDate;
    private LocalDateTime returnDate;
    private BigDecimal totalCost;
}
