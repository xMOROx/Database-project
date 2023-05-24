package org.agh.edu.pl.carrentalrestapi.controller;

import jakarta.validation.Valid;
import org.agh.edu.pl.carrentalrestapi.entity.Booking;
import org.agh.edu.pl.carrentalrestapi.exception.types.BookingNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.types.BookingUnavailableVehicleException;
import org.agh.edu.pl.carrentalrestapi.model.BookingModel;
import org.agh.edu.pl.carrentalrestapi.model.assembler.BookingModelAssembler;
import org.agh.edu.pl.carrentalrestapi.service.BookingService;
import org.agh.edu.pl.carrentalrestapi.utils.API_PATH;
import org.agh.edu.pl.carrentalrestapi.utils.PageableRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController
@RequestMapping(path = API_PATH.root + API_PATH.bookings)
public class BookingController {

    private final BookingService bookingService;
    private final BookingModelAssembler bookingModelAssembler;

    public BookingController(BookingService bookingService, BookingModelAssembler bookingModelAssembler) {
        this.bookingService = bookingService;
        this.bookingModelAssembler = bookingModelAssembler;
    }


    @GetMapping(path = "")
    @ResponseBody
    public ResponseEntity<PagedModel<BookingModel>> getAllBookings(@RequestParam(value = "page", required = false) Integer page,
                                                                   @RequestParam(value = "size", required = false) Integer size) {
        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);
        Page<Booking> bookings = bookingService.getAllBookings(pageable);

        return new ResponseEntity<>(
                BookingModelAssembler.toBookingModel(bookings),
                HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<BookingModel> getBookingById(@PathVariable("id") Long id) throws BookingNotFoundException {
        Booking booking = bookingService.getBookingsById(id);
        return Stream.of(booking).map(bookingModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .findFirst()
                .get();
    }

    @PostMapping(path = "")
    @ResponseBody
    public ResponseEntity<Long> addBooking(@Valid @RequestBody Booking booking) throws BookingUnavailableVehicleException {
        return new ResponseEntity<>(
                bookingService.addBooking(booking),
                HttpStatus.CREATED);
    }



    @DeleteMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteBooking(@PathVariable("id") Long id) throws BookingNotFoundException {
        bookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }

}


