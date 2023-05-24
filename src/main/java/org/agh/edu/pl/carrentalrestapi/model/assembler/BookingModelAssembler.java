package org.agh.edu.pl.carrentalrestapi.model.assembler;

import org.agh.edu.pl.carrentalrestapi.controller.BookingController;
import org.agh.edu.pl.carrentalrestapi.entity.Booking;
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
        return BookingModel.builder()
                .id(booking.getId())
                .userID(booking.getUser().getId())
                .vehicleID(booking.getVehicle().getId())
                .locationID(booking.getLocation().getId())
                .bookingStateCodeID(booking.getBookingStateCode().getId())
                .receiptDate(booking.getReceiptDate())
                .returnDate(booking.getReturnDate())
                .totalCost(booking.getTotalCost())
                .build();

    }

    public static PagedModel<BookingModel> toBookingModel(Page<Booking> bookings) {
        if (bookings.isEmpty()) {
            return PagedModel.empty();
        }

        int page = bookings.getNumber();
        int size = bookings.getSize();
        long totalElements = bookings.getTotalElements();
        long totalPages = bookings.getTotalPages();
        return PagedModel.of(bookings.stream().map(BookingModelAssembler::toBookingModel)
                        .collect(Collectors.toList()),
                new PagedModel.PageMetadata(size, page, totalElements, totalPages),
                linkTo(methodOn(BookingController.class).getAllBookings(page, size)).withSelfRel());
    }


    @Override
    public BookingModel toModel(Booking entity) {
        BookingModel bookingModel = BookingModelAssembler.toBookingModel(entity);
        bookingModel.add(linkTo(methodOn(BookingController.class).getBookingById(entity.getId())).withSelfRel());
        return bookingModel;
    }
}
