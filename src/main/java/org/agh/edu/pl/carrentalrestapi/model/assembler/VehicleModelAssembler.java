package org.agh.edu.pl.carrentalrestapi.model.assembler;

import org.agh.edu.pl.carrentalrestapi.controller.VehicleController;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.agh.edu.pl.carrentalrestapi.entity.VehicleParameters;
import org.agh.edu.pl.carrentalrestapi.entity.VehicleStatus;
import org.agh.edu.pl.carrentalrestapi.model.VehicleModel;
import org.agh.edu.pl.carrentalrestapi.model.VehicleParametersModel;
import org.agh.edu.pl.carrentalrestapi.model.VehicleStatusModel;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VehicleModelAssembler extends RepresentationModelAssemblerSupport<Vehicle, VehicleModel> {
    public VehicleModelAssembler() {
        super(VehicleController.class, VehicleModel.class);
    }

    public static VehicleModel toVehicleModel(Vehicle vehicle) {
        VehicleParameters parameters = vehicle.getVehicleParameters();

        VehicleParametersModel vehicleParameters = parameters == null ? null : VehicleParametersModelAssembler.toVehicleParametersModel(parameters);

        VehicleStatus vehicleStatus = vehicle.getVehicleStatus();
        VehicleStatusModel vehicleStatusId = vehicleStatus == null ? null : VehicleStatusModelAssembler.toVehicleStatusModel(vehicleStatus);

        vehicleParameters.setVehicleId(null);

        return VehicleModel.builder()
                .id(vehicle.getId())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .bestOffer(vehicle.getBestOffer())
                .dailyFee(vehicle.getDailyFee())
                .registration(vehicle.getRegistration())
                .vehicleParameters(vehicleParameters)
                .vehicleStatus(vehicleStatusId)
                .photoURL(vehicle.getPhotoURL())
                .build();
    }

    public static PagedModel<VehicleModel> toVehicleModel(Page<Vehicle> vehicle) {
        if (vehicle.isEmpty())
            return PagedModel.empty();

        int size = vehicle.getSize();
        int page = vehicle.getNumber();
        long totalElements = vehicle.getTotalElements();
        long totalPages = vehicle.getTotalPages();

        return PagedModel.of(
                vehicle.stream().map(VehicleModelAssembler::toVehicleModel).collect(Collectors.toList()),
                new PagedModel.PageMetadata(size, page, totalElements, totalPages),
                linkTo(methodOn(VehicleController.class).getAllVehicles(page, size)).withSelfRel()
        );
    }


    @Override
    public VehicleModel toModel(Vehicle entity) {
        VehicleModel vehicleModel = VehicleModelAssembler.toVehicleModel(entity);
        vehicleModel.add(linkTo(methodOn(VehicleController.class).getVehicleById(entity.getId())).withSelfRel());
        vehicleModel.add(linkTo(methodOn(VehicleController.class).getAllVehicles(null, null)).withRel("all"));
        return vehicleModel;
    }
}
