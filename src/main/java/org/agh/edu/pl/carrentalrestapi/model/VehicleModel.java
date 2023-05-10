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

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "Vehicles")
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "Vehicles")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleModel extends RepresentationModel<VehicleModel> {
    private Long id;
    private Long vehicleParametersId;
    private Long VehicleStatusId;
    private String registration;
    private String brand;
    private String model;
    private BigDecimal dailyFee;
    private Boolean bestOffer;
}
