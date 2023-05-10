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
@JsonRootName(value = "VehicleParameters")
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "VehicleParameters")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleParametersModel extends RepresentationModel<VehicleParametersModel> {
    private Long id;
    private Long vehicleId;
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
