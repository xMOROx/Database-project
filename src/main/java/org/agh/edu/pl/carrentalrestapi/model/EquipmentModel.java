package org.agh.edu.pl.carrentalrestapi.model;

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
@JsonRootName(value = "Equipments")
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "Equipments")
public class EquipmentModel extends RepresentationModel<EquipmentModel> {
    private Long id;
    private String equipmentCode;
    private String description;
}
