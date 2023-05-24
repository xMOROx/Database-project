package org.agh.edu.pl.carrentalrestapi.model.assembler;

import org.agh.edu.pl.carrentalrestapi.controller.UserController;
import org.agh.edu.pl.carrentalrestapi.controller.UserRoleController;
import org.agh.edu.pl.carrentalrestapi.entity.UserRole;
import org.agh.edu.pl.carrentalrestapi.model.UserRoleModel;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserRoleModelAssembler extends RepresentationModelAssemblerSupport<UserRole, UserRoleModel> {

    public UserRoleModelAssembler() {
        super(UserRoleController.class, UserRoleModel.class);
    }

    public static UserRoleModel toUserRoleModel(UserRole role) {
        return UserRoleModel.builder()
                .id(role.getId())
                .type(role.getType())
                .build();
    }

    public static PagedModel<UserRoleModel> toUserRoleModel(Page<UserRole> roles, Long userId) {
        if (roles.isEmpty()) {
            return PagedModel.empty();
        }
        int page = roles.getNumber();
        int size = roles.getSize();
        long totalElements = roles.getTotalElements();
        long totalPages = roles.getTotalPages();
        return PagedModel.of(roles.stream().map(UserRoleModelAssembler::toUserRoleModel)
                .collect(Collectors.toList()),
                new PagedModel.PageMetadata(size, page, totalElements, totalPages),
                linkTo(methodOn(UserController.class).getRolesForUser(userId, page, size)).withSelfRel());
    }

    @Override
    public UserRoleModel toModel(UserRole entity) {
        return  UserRoleModelAssembler.toUserRoleModel(entity);
    }
}
