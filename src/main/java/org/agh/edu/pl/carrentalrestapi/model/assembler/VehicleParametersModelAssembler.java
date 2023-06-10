package org.agh.edu.pl.carrentalrestapi.model.assembler;

import org.agh.edu.pl.carrentalrestapi.controller.VehicleParametersController;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.agh.edu.pl.carrentalrestapi.entity.VehicleParameters;
import org.agh.edu.pl.carrentalrestapi.model.VehicleParametersModel;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VehicleParametersModelAssembler extends RepresentationModelAssemblerSupport<VehicleParameters, VehicleParametersModel> {
    public VehicleParametersModelAssembler() {
        super(VehicleParametersController.class, VehicleParametersModel.class);
    }

    public static VehicleParametersModel toVehicleParametersModel(VehicleParameters vehicleParameters) {
        Vehicle vehicle = vehicleParameters.getVehicle();
        Long vehicleId = vehicle == null ? null : vehicleParameters.getVehicle().getId();

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
                .build();
    }

    public static PagedModel<VehicleParametersModel> toVehicleParametersModel(Page<VehicleParameters> vehicleParameters) {
        if(vehicleParameters.isEmpty())
            return PagedModel.empty();

        int size = vehicleParameters.getSize();
        int page = vehicleParameters.getNumber();
        long totalElements = vehicleParameters.getTotalElements();
        long totalPages = vehicleParameters.getTotalPages();

        return PagedModel.of(
                vehicleParameters.stream().map(VehicleParametersModelAssembler::toVehicleParametersModel).collect(Collectors.toList()),
                new PagedModel.PageMetadata(size, page, totalElements, totalPages),
                linkTo(methodOn(VehicleParametersController.class).getAllVehicleParameters(page, size)).withSelfRel()
        );
    }


    @Override
    public VehicleParametersModel toModel(VehicleParameters entity) {
        VehicleParametersModel vehicleParametersModel = VehicleParametersModelAssembler.toVehicleParametersModel(entity);

        vehicleParametersModel.add(linkTo(methodOn(VehicleParametersController.class).getVehicleParametersById(entity.getId())).withSelfRel());
        vehicleParametersModel.add(linkTo(methodOn(VehicleParametersController.class).getAllVehicleParameters(null, null)).withRel("all"));
        return vehicleParametersModel;
    }
}
