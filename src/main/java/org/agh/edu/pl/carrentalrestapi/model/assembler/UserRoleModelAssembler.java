package org.agh.edu.pl.carrentalrestapi.model.assembler;

import org.agh.edu.pl.carrentalrestapi.controller.UserRoleController;
import org.agh.edu.pl.carrentalrestapi.entity.UserRole;
import org.agh.edu.pl.carrentalrestapi.model.UserRoleModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UserRoleModelAssembler extends RepresentationModelAssemblerSupport<UserRole, UserRoleModel> {

    public UserRoleModelAssembler() {
        super(UserRoleController.class, UserRoleModel.class);
    }

    @Override
    public CollectionModel<UserRoleModel> toCollectionModel(Iterable<? extends UserRole> entities) {
        return super.toCollectionModel(entities);
    }

    @Override
    public UserRoleModel toModel(UserRole entity) {
        return null;
    }
}
