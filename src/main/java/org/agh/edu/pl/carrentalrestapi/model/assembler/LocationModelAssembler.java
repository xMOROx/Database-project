package org.agh.edu.pl.carrentalrestapi.model.assembler;

import org.agh.edu.pl.carrentalrestapi.controller.LocationController;
import org.agh.edu.pl.carrentalrestapi.entity.Equipment;
import org.agh.edu.pl.carrentalrestapi.entity.Location;
import org.agh.edu.pl.carrentalrestapi.model.EquipmentModel;
import org.agh.edu.pl.carrentalrestapi.model.LocationModel;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LocationModelAssembler extends RepresentationModelAssemblerSupport<Location, LocationModel> {
    public LocationModelAssembler() {
        super(LocationController.class, LocationModel.class);
    }
    public static LocationModel toLocationModel(Location location) {
        return LocationModel.builder()
                .id(location.getId())
                .country(location.getCountry())
                .city(location.getCity())
                .address(location.getAddress())
                .email(location.getEmail())
                .phoneNumber(location.getPhoneNumber())
                .openingHours(location.getOpeningHours())
                .closingHours(location.getClosingHours())
                .postalCode(location.getPostalCode())
                .build();
    }
    public static PagedModel<LocationModel> toLocationModel(Page<Location> location)
    {
        if (location.isEmpty())
            return PagedModel.empty();

        int size = location.getSize();
        int page = location.getNumber();

        long totalElements = location.getTotalElements();
        long totalPages = location.getTotalPages();

        return PagedModel.of(
                location.stream()
                        .map(LocationModelAssembler::toLocationModel)
                        .collect(java.util.stream.Collectors.toList()),
                new PagedModel.PageMetadata(size, page, totalElements, totalPages),
                linkTo(methodOn(LocationController.class).getAllLocations(page, size)).withSelfRel()
        );
    }
    @Override
    public LocationModel toModel(Location entity) {
        LocationModel locationModel = LocationModelAssembler.toLocationModel(entity);
        locationModel.add(linkTo(methodOn(LocationController.class).getLocationById(entity.getId())).withSelfRel());
        return locationModel;
    }
}
