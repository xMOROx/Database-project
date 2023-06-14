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
@JsonRootName(value = "VehicleStatuses")
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "VehicleStatuses")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleStatusModel extends RepresentationModel<VehicleStatusModel> {
    private Long id;
    private String type;
    private String description;
}
