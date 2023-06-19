package org.agh.edu.pl.carrentalrestapi.model.assembler;

import org.agh.edu.pl.carrentalrestapi.controller.VehicleController;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.agh.edu.pl.carrentalrestapi.entity.VehicleStatus;
import org.agh.edu.pl.carrentalrestapi.model.VehicleModel;
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
        VehicleStatus vehicleStatus = vehicle.getVehicleStatus();
        VehicleStatusModel vehicleStatusId = vehicleStatus == null ? null : VehicleStatusModelAssembler.toVehicleStatusModel(vehicleStatus);


        return VehicleModel.builder()
                .id(vehicle.getId())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .bestOffer(vehicle.getBestOffer())
                .dailyFee(vehicle.getDailyFee())
                .registration(vehicle.getRegistration())
                .bodyType(vehicle.getBodyType())
                .productionYear(vehicle.getProductionYear())
                .fuelType(vehicle.getFuelType())
                .power(vehicle.getPower())
                .gearbox(vehicle.getGearbox())
                .frontWheelDrive(vehicle.getFrontWheelDrive())
                .doorsNumber(vehicle.getDoorsNumber())
                .seatsNumber(vehicle.getSeatsNumber())
                .color(vehicle.getColor())
                .metalic(vehicle.getMetalic())
                .description(vehicle.getDescription())
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
