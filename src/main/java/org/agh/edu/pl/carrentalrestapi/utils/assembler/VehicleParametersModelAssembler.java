package org.agh.edu.pl.carrentalrestapi.utils.assembler;

import org.agh.edu.pl.carrentalrestapi.controller.VehicleParametersController;
import org.agh.edu.pl.carrentalrestapi.entity.VehicleParameters;
import org.agh.edu.pl.carrentalrestapi.model.VehicleParametersModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VehicleParametersModelAssembler extends RepresentationModelAssemblerSupport<VehicleParameters, VehicleParametersModel> {
    public VehicleParametersModelAssembler() {
        super(VehicleParametersController.class, VehicleParametersModel.class);
    }

    public static VehicleParametersModel toVehicleParametersModel(VehicleParameters vehicleParameters) {
        Long vehicleId = vehicleParameters.getVehicle() == null ? null : vehicleParameters.getVehicle().getId();

        return VehicleParametersModel.builder()
                .id(vehicleParameters.getId())
                .vehicleId(vehicleId)
                .bodyType(vehicleParameters.getBodyType())
                .productionYear(vehicleParameters.getProductionYear())
                .fuelType(vehicleParameters.getFuelType())
                .power(vehicleParameters.getPower())
                .gearbox(vehicleParameters.getGearbox())
                .frontWheelDrive(vehicleParameters.getFrontWheelDrive())
                .doorsNumber(vehicleParameters.getDoorsNumber())
                .seatsNumber(vehicleParameters.getSeatsNumber())
                .color(vehicleParameters.getColor())
                .metalic(vehicleParameters.getMetalic())
                .description(vehicleParameters.getDescription())
                .photoURL(vehicleParameters.getPhotoURL())
                .build();
    }

    public static List<VehicleParametersModel> toVehicleModel(List<VehicleParameters> vehicleParameters) {
        if (vehicleParameters == null) {
            return Collections.emptyList();
        }

        return vehicleParameters.stream()
                .map(VehicleParametersModelAssembler::toVehicleParametersModel)
                .collect(Collectors.toList());
    }

    @Override
    public CollectionModel<VehicleParametersModel> toCollectionModel(Iterable<? extends VehicleParameters> entities) {
        CollectionModel<VehicleParametersModel> vehicleParametersModels = super.toCollectionModel(entities);
        vehicleParametersModels.add(linkTo(methodOn(VehicleParametersController.class).getAllVehicleParameters(null, null)).withSelfRel());
        return vehicleParametersModels;
    }

    @Override
    public VehicleParametersModel toModel(VehicleParameters entity) {
        VehicleParametersModel vehicleParametersModel = VehicleParametersModelAssembler.toVehicleParametersModel(entity);

        vehicleParametersModel.add(linkTo(methodOn(VehicleParametersController.class).getVehicleParametersById(entity.getId())).withSelfRel());

        return vehicleParametersModel;
    }
}
