package org.agh.edu.pl.carrentalrestapi.model.assembler;

import org.agh.edu.pl.carrentalrestapi.controller.BestOfferController;
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
public class VehicleBestOfferModelAssembler extends RepresentationModelAssemblerSupport<Vehicle, VehicleModel> {
    public VehicleBestOfferModelAssembler() {
        super(BestOfferController.class, VehicleModel.class);
    }

    public static VehicleModel toVehicleModel(Vehicle vehicle) {
        VehicleStatus vehicleStatus = vehicle.getVehicleStatus();
        VehicleStatusModel VehicleStatusId = vehicleStatus == null ? null : VehicleStatusModelAssembler.toVehicleStatusModel(vehicleStatus);

        return VehicleModel.builder()
                .id(vehicle.getId())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .bestOffer(vehicle.getBestOffer())
                .dailyFee(vehicle.getDailyFee())
                .registration(vehicle.getRegistration())
                .vehicleStatus(VehicleStatusId)
                .photoURL(vehicle.getPhotoURL())
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
                .build();
    }

    public static PagedModel<VehicleModel> toVehicleModel(Page<Vehicle> vehicles) {
        if (vehicles.isEmpty()) {
            return PagedModel.empty();
        }

        int page = vehicles.getNumber();
        int size = vehicles.getSize();
        long totalElements = vehicles.getTotalElements();
        long totalPages = vehicles.getTotalPages();
        return PagedModel.of(
                vehicles.stream()
                        .map(VehicleBestOfferModelAssembler::toVehicleModel)
                        .collect(Collectors.toList()),
                new PagedModel.PageMetadata(size, page, totalElements, totalPages),
                linkTo(methodOn(BestOfferController.class).getBestOffer(page, size)).withSelfRel()

        );
    }


    @Override
    public VehicleModel toModel(Vehicle entity) {
        return VehicleBestOfferModelAssembler.toVehicleModel(entity);
    }
}
