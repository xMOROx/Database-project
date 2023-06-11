package org.agh.edu.pl.carrentalrestapi.model.assembler;

import org.agh.edu.pl.carrentalrestapi.controller.BookingController;
import org.agh.edu.pl.carrentalrestapi.controller.EquipmentController;
import org.agh.edu.pl.carrentalrestapi.entity.Booking;
import org.agh.edu.pl.carrentalrestapi.entity.Equipment;
import org.agh.edu.pl.carrentalrestapi.model.BookingModel;
import org.agh.edu.pl.carrentalrestapi.model.EquipmentModel;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
public class EquipmentModelAssembler extends RepresentationModelAssemblerSupport<Equipment, EquipmentModel>{
    public EquipmentModelAssembler() {
        super(EquipmentController.class, EquipmentModel.class);
    }
    
    public static EquipmentModel toEquipmentModel(Equipment equipment) {
        return EquipmentModel.builder()
                .id(equipment.getId())
                .equipmentCode(equipment.getEquipmentCode())
                .description(equipment.getDescription()).build();

    }

    public static PagedModel<EquipmentModel> toEquipmentModel(Page<Equipment> equipment) {
        if (equipment.isEmpty())
            return PagedModel.empty();

        int size = equipment.getSize();
        int page = equipment.getNumber();
        long totalElements = equipment.getTotalElements();
        long totalPages = equipment.getTotalPages();

        return PagedModel.of(
                equipment.stream()
                        .map(EquipmentModelAssembler::toEquipmentModel)
                        .collect(Collectors.toList()),
                new PagedModel.PageMetadata(size, page, totalElements, totalPages),
                linkTo(methodOn(EquipmentController.class).getAllEquipments(page, size)).withSelfRel()
        );
    }

    @Override
    public EquipmentModel toModel(Equipment entity) {
        EquipmentModel equipmentModel = EquipmentModelAssembler.toEquipmentModel(entity);
        equipmentModel.add(linkTo(methodOn(EquipmentController.class).getEquipmentById(entity.getId())).withSelfRel());
        equipmentModel.add(linkTo(methodOn(EquipmentController.class).getAllEquipments(null, null)).withRel("all"));
        return equipmentModel;
    }
}
