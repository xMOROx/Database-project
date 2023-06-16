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
@JsonRootName(value = "Locations")
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "Locations")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationModel extends RepresentationModel<LocationModel> {
    private Long id;
    private String country;
    private String city;
    private String address;
    private String email;
    private String phoneNumber;
    private String openingHours;
    private String closingHours;
    private String postalCode;
    private String photoURL;

}
