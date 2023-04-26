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
@JsonRootName(value = "Users")
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "Users")
public class UserModel extends RepresentationModel<UserModel> {
    private Long id;
    private String firstName;
    private String surName;
    private String email;
    private String login;
//    private String password; //TODO: remove password from UserModel because it will be used in controllers and it is not safe
    private String phoneNumber;
    private String pesel;
}
