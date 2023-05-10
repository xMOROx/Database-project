package org.agh.edu.pl.carrentalrestapi.service;

import org.agh.edu.pl.carrentalrestapi.entity.Booking;
import org.agh.edu.pl.carrentalrestapi.exception.types.BookingNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.types.BookingUnavailableVehicleException;
import org.agh.edu.pl.carrentalrestapi.exception.types.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingService {
    Long addBooking(Booking booking) throws BookingUnavailableVehicleException;

    void cancelBooking(Long bookingId) throws BookingNotFoundException;

    void bookingRent(Long bookingId) throws BookingNotFoundException;

    void bookingReturn(Long bookingId) throws BookingNotFoundException;

    Page<Booking> getBookings(Pageable pageable);

    Page<Booking> getBookingsRented(Pageable pageable);

    Page<Booking> getBookingsReserved(Pageable pageable);

    Page<Booking> getBookingsCanceledForPage(Pageable pageable);

    Page<Booking> getBookingsReturned(Pageable pageable);

    Booking getBookingsById(Long bookingId) throws BookingNotFoundException;

    Page<Booking> getAllBookings(Pageable pageable);

    Page<Booking> getUserBookings(Long userId, Pageable pageable) throws UserNotFoundException;

    Page<Booking> getUserBookingsReserved(Long userId, Pageable pageable) throws UserNotFoundException;

    Page<Booking> getUserBookingsRented(Long userId, Pageable pageable) throws UserNotFoundException;

}
