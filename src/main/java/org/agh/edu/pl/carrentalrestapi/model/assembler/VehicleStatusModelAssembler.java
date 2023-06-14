package org.agh.edu.pl.carrentalrestapi.model.assembler;

import org.agh.edu.pl.carrentalrestapi.controller.VehicleStatusController;
import org.agh.edu.pl.carrentalrestapi.entity.VehicleStatus;
import org.agh.edu.pl.carrentalrestapi.model.VehicleStatusModel;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VehicleStatusModelAssembler extends RepresentationModelAssemblerSupport<VehicleStatus, VehicleStatusModel> {
    public VehicleStatusModelAssembler() {
        super(VehicleStatusController.class, VehicleStatusModel.class);
    }

    public static VehicleStatusModel toVehicleStatusModel(VehicleStatus status) {
        return VehicleStatusModel.builder()
                .id(status.getId())
                .type(status.getType())
                .description(status.getDescription())
                .build();
    }

    public static PagedModel<VehicleStatusModel> toVehicleStatusModel(Page<VehicleStatus> statuses) {
        if (statuses.isEmpty()) {
            return PagedModel.empty();
        }

        int page = statuses.getNumber();
        int size = statuses.getSize();
        long totalElements = statuses.getTotalElements();
        long totalPages = statuses.getTotalPages();

        return PagedModel.of(statuses
                        .stream()
                        .map(VehicleStatusModelAssembler::toVehicleStatusModel)
                        .collect(Collectors.toList()),
                new PagedModel.PageMetadata(size, page, totalElements, totalPages));
    }

    @Override
    public VehicleStatusModel toModel(VehicleStatus entity) {
        VehicleStatusModel vehicleStatusModel = VehicleStatusModelAssembler.toVehicleStatusModel(entity);

        vehicleStatusModel.add(linkTo(methodOn(VehicleStatusController.class).getVehicleStatus(entity.getId())).withSelfRel());
        vehicleStatusModel.add(linkTo(methodOn(VehicleStatusController.class).getAllVehicleStatuses(null, null)).withRel("statuses"));
        return vehicleStatusModel;
    }
}
