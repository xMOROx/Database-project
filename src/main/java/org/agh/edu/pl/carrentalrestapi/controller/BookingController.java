package org.agh.edu.pl.carrentalrestapi.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.agh.edu.pl.carrentalrestapi.exception.types.BookingNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.types.BookingUnavailableVehicleException;
import org.agh.edu.pl.carrentalrestapi.exception.types.VehicleNotFoundException;
import org.agh.edu.pl.carrentalrestapi.model.BookingModel;
import org.agh.edu.pl.carrentalrestapi.model.ReserveVehicleModel;
import org.agh.edu.pl.carrentalrestapi.model.assembler.BookingModelAssembler;
import org.agh.edu.pl.carrentalrestapi.service.BookingService;
import org.agh.edu.pl.carrentalrestapi.utils.API_PATH;
import org.agh.edu.pl.carrentalrestapi.utils.PageableRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Map;

@Controller
@RequestMapping(path = API_PATH.root + API_PATH.bookings)
@Slf4j
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(path = "/reserve")
    @ResponseBody
    public ResponseEntity<String> reserveVehicle(@Valid @RequestBody ReserveVehicleModel reservation)
            throws BookingUnavailableVehicleException {


        Long bookingId = bookingService.addBooking(reservation);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(bookingId).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "")
    @ResponseBody
    public ResponseEntity<PagedModel<BookingModel>> getAllBookings(@RequestParam(value = "page", required = false) Integer page,
                                                                   @RequestParam(value = "size", required = false) Integer size) {

        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        return ResponseEntity.ok(
                BookingModelAssembler.toBookingModel(bookingService.getBookings(pageable))
        );
    }

    @GetMapping(value = "/reserved")
    @ResponseBody
    public ResponseEntity<PagedModel<BookingModel>> getReservedBookings(@RequestParam(value = "page", required = false) Integer page,
                                                                        @RequestParam(value = "size", required = false) Integer size) {
        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        return ResponseEntity.ok(
                BookingModelAssembler.toBookingModel(bookingService.getBookingsReserved(pageable))
        );
    }

    @GetMapping(value = "/rented")
    @ResponseBody
    public ResponseEntity<PagedModel<BookingModel>> getRentedBookings(@RequestParam(value = "page", required = false) Integer page,
                                                                      @RequestParam(value = "size", required = false) Integer size) {
        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        return ResponseEntity.ok(
                BookingModelAssembler.toBookingModel(bookingService.getBookingsRented(pageable))
        );
    }

    @GetMapping(value = "/returned")
    @ResponseBody
    public ResponseEntity<PagedModel<BookingModel>> getReturnedBookings(@RequestParam(value = "page", required = false) Integer page,
                                                                        @RequestParam(value = "size", required = false) Integer size) {
        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        return ResponseEntity.ok(
                BookingModelAssembler.toBookingModel(bookingService.getBookingsReturned(pageable))
        );
    }

    @GetMapping(value = "/canceled")
    @ResponseBody
    public ResponseEntity<PagedModel<BookingModel>> getCanceledBookings(@RequestParam(value = "page", required = false) Integer page,
                                                                        @RequestParam(value = "size", required = false) Integer size) {
        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        return ResponseEntity.ok(
                BookingModelAssembler.toBookingModel(bookingService.getBookingsCanceled(pageable))
        );
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<BookingModel> getBookingById(@PathVariable Long id) throws BookingNotFoundException {
        return ResponseEntity.ok(BookingModelAssembler.toBookingModel(bookingService.getBookingById(id)));
    }

    @PostMapping(value = "/cancel")
    @ResponseBody
    public ResponseEntity<String> cancelBooking(@RequestBody Long id) throws BookingNotFoundException {
        bookingService.cancelBooking(id);
        return ResponseEntity.ok("Booking with id: " + id + " canceled");
    }

    @PostMapping(value = "/rent")
    @ResponseBody
    public ResponseEntity<String> rentBooking(@RequestBody Long id) throws BookingNotFoundException {
        bookingService.rentBooking(id);
        return ResponseEntity.ok("Booking with id: " + id + " rented");
    }

    @PostMapping(value = "/return")
    @ResponseBody
    public ResponseEntity<String> returnBooking(@RequestBody Long id) throws BookingNotFoundException {
        bookingService.returnBooking(id);
        return ResponseEntity.ok("Booking with id: " + id + " returned");
    }

    @GetMapping(value = "/active")
    @ResponseBody
    public ResponseEntity<PagedModel<BookingModel>> getActiveBookings(@RequestParam(value = "page", required = false) Integer page,
                                                                      @RequestParam(value = "size", required = false) Integer size) {
        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        return ResponseEntity.ok(
                BookingModelAssembler.toBookingModel(bookingService.activeBookings(pageable))
        );
    }

    @PostMapping(value = "/cost")
    @ResponseBody
    public ResponseEntity<Map<String, BigDecimal>> countCost(@RequestBody Long bookingId)
            throws BookingNotFoundException, VehicleNotFoundException {

        return ResponseEntity.ok(bookingService.countCost(bookingId));
    }

}