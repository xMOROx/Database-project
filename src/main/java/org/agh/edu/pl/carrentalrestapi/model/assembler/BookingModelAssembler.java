package org.agh.edu.pl.carrentalrestapi.model.assembler;

import org.agh.edu.pl.carrentalrestapi.controller.BookingController;
import org.agh.edu.pl.carrentalrestapi.entity.*;
import org.agh.edu.pl.carrentalrestapi.model.*;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookingModelAssembler extends RepresentationModelAssemblerSupport<Booking, BookingModel> {
    public BookingModelAssembler() {
        super(BookingController.class, BookingModel.class);
    }

    public static BookingModel toBookingModel(Booking booking) {
        User user = booking.getUser();
        UserModel userModel = user == null ? null : UserModelAssembler.toUserModel(user);

        Vehicle vehicle = booking.getVehicle();
        VehicleModel vehicleModel = vehicle == null ? null : VehicleModelAssembler.toVehicleModel(vehicle);

        Location location = booking.getLocation();
        LocationModel locationModel = location == null ? null : LocationModelAssembler.toLocationModel(location);

        BookingStateCode bookingStateCode = booking.getBookingStateCode();

        BookingStateCodeModel bookingStateCodeModel = bookingStateCode == null ? null : BookingStateCodeModel.builder()
                .id(bookingStateCode.getId())
                .bookingCode(bookingStateCode.getBookingCode())
                .description(bookingStateCode.getDescription())
                .build();

        return BookingModel.builder()
                .id(booking.getId())
                .user(userModel)
                .vehicle(vehicleModel)
                .pickupLocation(locationModel)
                .bookingStateCode(bookingStateCodeModel)
                .receiptDate(booking.getReceiptDate())
                .returnDate(booking.getReturnDate())
                .totalCost(booking.getTotalCost()).build();
    }

    public static PagedModel<BookingModel> toBookingModel(Page<Booking> booking) {
        if (booking.isEmpty())
            return PagedModel.empty();

        int size = booking.getSize();
        int page = booking.getNumber();
        long totalElements = booking.getTotalElements();
        long totalPages = booking.getTotalPages();

        return PagedModel.of(
                booking.stream()
                        .map(BookingModelAssembler::toBookingModel)
                        .collect(Collectors.toList()),
                new PagedModel.PageMetadata(size, page, totalElements, totalPages),
                linkTo(methodOn(BookingController.class).getAllBookings(page, size)).withSelfRel()
        );
    }

    @Override
    public BookingModel toModel(Booking entity) {
        BookingModel bookingModel = BookingModelAssembler.toBookingModel(entity);
        bookingModel.add(linkTo(methodOn(BookingController.class).getBookingById(entity.getId())).withSelfRel());
        bookingModel.add(linkTo(methodOn(BookingController.class).getAllBookings(null, null)).withRel("all"));
        return bookingModel;
    }
}
