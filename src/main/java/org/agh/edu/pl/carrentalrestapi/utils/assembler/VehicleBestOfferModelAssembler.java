package org.agh.edu.pl.carrentalrestapi.utils.assembler;

import org.agh.edu.pl.carrentalrestapi.controller.BestOfferController;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
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
public class VehicleBestOfferModelAssembler extends RepresentationModelAssemblerSupport<Vehicle, VehicleModel> {
    public VehicleBestOfferModelAssembler() {
        super(BestOfferController.class, VehicleModel.class);
    }

    public static VehicleModel toVehicleModel(Vehicle vehicle) {
        return VehicleModel.builder()
                .id(vehicle.getId())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .bestOffer(vehicle.getBestOffer())
                .dailyFee(vehicle.getDailyFee())
                .registration(vehicle.getRegistration())
                .vehicleParametersId(vehicle.getVehicleParameters().getId())
                .VehicleStatusId(vehicle.getVehicleStatus().getId())
                .build();
    }

    public static List<VehicleModel> toVehicleModel(List<Vehicle> vehicles) {
        if (vehicles.isEmpty()) {
            return Collections.emptyList();
        }

        return vehicles.stream()
                .map(VehicleBestOfferModelAssembler::toVehicleModel)
                .collect(Collectors.toList());
    }

    @Override
    public CollectionModel<VehicleModel> toCollectionModel(Iterable<? extends Vehicle> entities) {
        CollectionModel<VehicleModel> vehicleModels = super.toCollectionModel(entities);
        vehicleModels.add(linkTo(methodOn(BestOfferController.class).getBestOffer(0, 1)).withSelfRel());
        return vehicleModels;
    }

    @Override
    public VehicleModel toModel(Vehicle entity) {
        return VehicleBestOfferModelAssembler.toVehicleModel(entity);
    }
}
