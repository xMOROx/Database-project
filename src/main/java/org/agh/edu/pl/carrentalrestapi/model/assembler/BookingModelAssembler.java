package org.agh.edu.pl.carrentalrestapi.model.assembler;

import org.agh.edu.pl.carrentalrestapi.controller.BookingController;
import org.agh.edu.pl.carrentalrestapi.entity.*;
import org.agh.edu.pl.carrentalrestapi.model.BookingModel;
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
        Long userID = user == null ? null : user.getId();

        Vehicle vehicle = booking.getVehicle();
        Long vehicleID = vehicle == null ? null : vehicle.getId();

        Location location = booking.getLocation();
        Long locationID = location == null ? null : location.getId();

        BookingStateCode bookingStateCode = booking.getBookingStateCode();
        Long bookingStateCodeID = bookingStateCode == null ? null : bookingStateCode.getId();

        return BookingModel.builder()
                .id(booking.getId())
                .userID(userID)
                .vehicleID(vehicleID)
                .locationID(locationID)
                .bookingStateCodeID(bookingStateCodeID)
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
        return bookingModel;
    }
}
