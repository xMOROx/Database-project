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
@JsonRootName(value = "UserRoles")
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "UserRoles")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRoleModel extends RepresentationModel<UserRoleModel> {
    private Long id;
    private String type;
}
