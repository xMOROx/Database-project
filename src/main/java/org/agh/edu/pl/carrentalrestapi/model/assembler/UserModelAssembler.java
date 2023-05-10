package org.agh.edu.pl.carrentalrestapi.model.assembler;

import org.agh.edu.pl.carrentalrestapi.controller.UserController;
import org.agh.edu.pl.carrentalrestapi.entity.User;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.agh.edu.pl.carrentalrestapi.model.UserModel;
import org.agh.edu.pl.carrentalrestapi.model.VehicleModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<UserModel> toUserModel(List<User> users) {
        if (users.isEmpty()) {
            return Collections.emptyList();
        }

        return users.stream()
                .map(UserModelAssembler::toUserModel)
                .collect(Collectors.toList());
    }
    @Override
    public CollectionModel<UserModel> toCollectionModel(Iterable<? extends User> entities) {
        CollectionModel<UserModel> userModels = super.toCollectionModel(entities);
//        userModels.add(linkTo(methodOn(UserController.class)))
        return userModels;
    }

    @Override
    public UserModel toModel(User entity) {
        return UserModelAssembler.toUserModel(entity);
    }
}
