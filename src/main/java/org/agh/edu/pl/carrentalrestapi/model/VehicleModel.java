package org.agh.edu.pl.carrentalrestapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
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
    private VehicleStatusModel vehicleStatus;
    private String registration;
    private String brand;
    private String model;
    private BigDecimal dailyFee;
    private Boolean bestOffer;
    private String bodyType;
    private Integer productionYear;
    private String fuelType;
    private Integer power;
    private String gearbox;
    private Boolean frontWheelDrive;
    private Integer doorsNumber;
    private Integer seatsNumber;
    private String color;
    private Boolean metalic;
    private String description;
    private String photoURL;
}
