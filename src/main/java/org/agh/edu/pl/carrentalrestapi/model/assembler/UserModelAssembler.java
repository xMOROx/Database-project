package org.agh.edu.pl.carrentalrestapi.model.assembler;

import org.agh.edu.pl.carrentalrestapi.controller.UserController;
import org.agh.edu.pl.carrentalrestapi.entity.User;
import org.agh.edu.pl.carrentalrestapi.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;


import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<User, UserModel> {
    public UserModelAssembler() {
        super(UserController.class, UserModel.class);
    }

    public static UserModel toUserModel(User user) {
        return UserModel.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .surName(user.getSurName())
                .email(user.getEmail())
                .login(user.getLogin())
                .phoneNumber(user.getPhoneNumber())
                .pesel(user.getPesel()).build();
    }

    public static PagedModel<UserModel> toUserModel(Page<User> users) {
        if (users.isEmpty()) {
            return PagedModel.empty();
        }

        int page = users.getNumber();
        int size = users.getSize();
        long totalElements = users.getTotalElements();
        long totalPages = users.getTotalPages();
        return PagedModel.of(users.stream().map(UserModelAssembler::toUserModel)
                .collect(Collectors.toList()),
                new PagedModel.PageMetadata(size, page, totalElements, totalPages),
                linkTo(methodOn(UserController.class).getAllUsers(page, size)).withSelfRel());
    }

    @Override
    public UserModel toModel(User entity) {
        UserModel userModel = UserModelAssembler.toUserModel(entity);
        userModel.add(linkTo(methodOn(UserController.class).getUserById(entity.getId())).withSelfRel());
        return userModel;
    }
}
