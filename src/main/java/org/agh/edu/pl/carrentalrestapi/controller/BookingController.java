package org.agh.edu.pl.carrentalrestapi.controller;

import org.agh.edu.pl.carrentalrestapi.entity.Booking;
import org.agh.edu.pl.carrentalrestapi.exception.types.BookingNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.types.BookingUnavailableVehicleException;
import org.agh.edu.pl.carrentalrestapi.exception.types.UserNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.types.VehicleNotFoundException;
import org.agh.edu.pl.carrentalrestapi.model.BookingModel;
import org.agh.edu.pl.carrentalrestapi.model.assembler.BookingModelAssembler;
import org.agh.edu.pl.carrentalrestapi.service.BookingService;
import org.agh.edu.pl.carrentalrestapi.utils.API_PATH;
import org.agh.edu.pl.carrentalrestapi.utils.PageableRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping(path = API_PATH.root + API_PATH.booking)
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(path = "/reserve")
    public ResponseEntity<String> reserveVehicle(@RequestBody Booking booking) {
        Long bookingId;

        try {
            bookingId = bookingService.addBooking(booking);
        } catch (BookingUnavailableVehicleException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.ok(bookingId.toString());
    }

    @GetMapping(value = "/all", params = {"page", "size"})
    public ResponseEntity<PagedModel<BookingModel>> getAllBookings(@RequestParam(value = "page", required = false) Integer page,
                                                                   @RequestParam(value = "size", required = false) Integer size) {
        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        return ResponseEntity.ok(
                BookingModelAssembler.toBookingModel(bookingService.getBookings(pageable))
        );
    }

    @GetMapping(value = "/reserved", params = {"page", "size"})
    public ResponseEntity<PagedModel<BookingModel>> getReservedBookings(@RequestParam(value = "page", required = false) Integer page,
                                                                        @RequestParam(value = "size", required = false) Integer size) {
        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        return ResponseEntity.ok(
                BookingModelAssembler.toBookingModel(bookingService.getBookingsReserved(pageable))
        );
    }

    @GetMapping(value = "/rented", params = {"page", "size"})
    public ResponseEntity<PagedModel<BookingModel>> getRentedBookings(@RequestParam(value = "page", required = false) Integer page,
                                                                      @RequestParam(value = "size", required = false) Integer size) {
        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        return ResponseEntity.ok(
                BookingModelAssembler.toBookingModel(bookingService.getBookingsRented(pageable))
        );
    }

    @GetMapping(value = "/returned", params = {"page", "size"})
    public ResponseEntity<PagedModel<BookingModel>> getReturnedBookings(@RequestParam(value = "page", required = false) Integer page,
                                                                        @RequestParam(value = "size", required = false) Integer size) {
        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        return ResponseEntity.ok(
                BookingModelAssembler.toBookingModel(bookingService.getBookingsReturned(pageable))
        );
    }

    @GetMapping(value = "/canceled", params = {"page", "size"})
    public ResponseEntity<PagedModel<BookingModel>> getCanceledBookings(@RequestParam(value = "page", required = false) Integer page,
                                                                        @RequestParam(value = "size", required = false) Integer size) {
        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        return ResponseEntity.ok(
                BookingModelAssembler.toBookingModel(bookingService.getBookingsCanceled(pageable))
        );
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookingModel> getBookingById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(BookingModelAssembler.toBookingModel(bookingService.getBookingById(id)));
        } catch (BookingNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/{id}/cancel")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
        try {
            bookingService.cancelBooking(id);
            return ResponseEntity.ok("Booking with id: " + id + " canceled");
        } catch (BookingNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(value = "/{id}/rent")
    public ResponseEntity<String> rentBooking(@PathVariable Long id) {
        try {
            bookingService.rentBooking(id);
            return ResponseEntity.ok("Booking with id: " + id + " rented");
        } catch (BookingNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(value = "/{id}/return")
    public ResponseEntity<String> returnBooking(@PathVariable Long id) {
        try {
            bookingService.returnBooking(id);
            return ResponseEntity.ok("Booking with id: " + id + " returned");
        } catch (BookingNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<PagedModel<BookingModel>> getBookingsByUserId(@PathVariable Long userId,
                                                                        @RequestParam(value = "page", required = false) Integer page,
                                                                        @RequestParam(value = "size", required = false) Integer size) {
        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        try {
            return ResponseEntity.ok(
                    BookingModelAssembler.toBookingModel(bookingService.getUserBookings(userId, pageable)));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/{userId}/reserved")
    public ResponseEntity<PagedModel<BookingModel>> getReservedBookingsByUserId(@PathVariable Long userId,
                                                                                @RequestParam(value = "page", required = false) Integer page,
                                                                                @RequestParam(value = "size", required = false) Integer size) {
        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        try {
            return ResponseEntity.ok(
                    BookingModelAssembler.toBookingModel(bookingService.getUserBookingsReserved(userId, pageable)));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/{userId}/rented")
    public ResponseEntity<PagedModel<BookingModel>> getRentedBookingsByUserId(@PathVariable Long userId,
                                                                              @RequestParam(value = "page", required = false) Integer page,
                                                                              @RequestParam(value = "size", required = false) Integer size) {
        PageableRequest pageableRequest = PageableRequest.of(page, size);
        Pageable pageable = PageableRequest.toPageable(pageableRequest);

        try {
            return ResponseEntity.ok(
                    BookingModelAssembler.toBookingModel(bookingService.getUserBookingsRented(userId, pageable)));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/cost")
    public ResponseEntity<Map<String, BigDecimal>> countCost(@RequestBody Booking booking) {
        try {
            return ResponseEntity.ok(bookingService.countCost(booking));
        } catch (BookingNotFoundException | VehicleNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
