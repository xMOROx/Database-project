package org.agh.edu.pl.carrentalrestapi.model.assembler;

import org.agh.edu.pl.carrentalrestapi.controller.VehicleController;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.agh.edu.pl.carrentalrestapi.entity.VehicleParameters;
import org.agh.edu.pl.carrentalrestapi.entity.VehicleStatus;
import org.agh.edu.pl.carrentalrestapi.model.VehicleModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VehicleModelAssembler extends RepresentationModelAssemblerSupport<Vehicle, VehicleModel> {
    public VehicleModelAssembler() {
        super(VehicleController.class, VehicleModel.class);
    }

    public static VehicleModel toVehicleModel(Vehicle vehicle) {
        VehicleParameters vehicleParameters = vehicle.getVehicleParameters();
        Long vehicleParametersId = vehicleParameters == null ? null : vehicle.getId();

        VehicleStatus vehicleStatus = vehicle.getVehicleStatus();
        Long vehicleStatusId = vehicleStatus == null ? null : vehicleStatus.getId();

        return VehicleModel.builder()
                .id(vehicle.getId())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .bestOffer(vehicle.getBestOffer())
                .dailyFee(vehicle.getDailyFee())
                .registration(vehicle.getRegistration())
                .vehicleParametersId(vehicleParametersId)
                .VehicleStatusId(vehicleStatusId)
                .build();
    }

    public static List<VehicleModel> toVehicleModel(List<Vehicle> vehicle) {
        if(vehicle.isEmpty())
            return Collections.emptyList();

        return vehicle.stream()
                .map(VehicleModelAssembler::toVehicleModel)
                .collect(Collectors.toList());
    }

    @Override
    public CollectionModel<VehicleModel> toCollectionModel(Iterable<? extends Vehicle> entities) {
        CollectionModel<VehicleModel> vehicleModels = super.toCollectionModel(entities);
        vehicleModels.add(linkTo(methodOn(VehicleController.class).getAllVehicles(null)).withSelfRel());
        return vehicleModels;
    }

    @Override
    public VehicleModel toModel(Vehicle entity) {
        VehicleModel vehicleModel = VehicleModelAssembler.toVehicleModel(entity);
        vehicleModel.add(linkTo(methodOn(VehicleController.class).getVehicleById(entity.getId())).withSelfRel());
        return vehicleModel;
    }
}
