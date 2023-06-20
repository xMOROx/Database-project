package org.agh.edu.pl.carrentalrestapi.service;

import org.agh.edu.pl.carrentalrestapi.entity.Booking;
import org.agh.edu.pl.carrentalrestapi.exception.types.*;
import org.agh.edu.pl.carrentalrestapi.model.ReserveVehicleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Map;

public interface BookingService {
    Long addBooking(ReserveVehicleModel reservation) throws BookingUnavailableVehicleException, VehicleNotFoundException, UserNotFoundException, LocationNotFoundException;

    void cancelBooking(Long bookingId) throws BookingNotFoundException;

    void rentBooking(Long bookingId) throws BookingNotFoundException;

    void returnBooking(Long bookingId) throws BookingNotFoundException;

    Page<Booking> getBookings(Pageable pageable);

    Page<Booking> getBookingsRented(Pageable pageable);

    Page<Booking> getBookingsReserved(Pageable pageable);

    Page<Booking> getBookingsCanceled(Pageable pageable);

    Page<Booking> getBookingsReturned(Pageable pageable);

    Booking getBookingById(Long bookingId) throws BookingNotFoundException;

    Page<Booking> getUserBookings(Long userId, Pageable pageable) throws UserNotFoundException;

    Page<Booking> getUserBookingsReserved(Long userId, Pageable pageable) throws UserNotFoundException;

    Page<Booking> getUserBookingsRented(Long userId, Pageable pageable) throws UserNotFoundException;
    Page<Booking> activeBookings(Pageable pageable);
    Page<Booking> activeBookingsByUserId(Long userId, Pageable pageable) throws UserNotFoundException;

    Map<String, BigDecimal> countCost(Long bookingId ) throws BookingNotFoundException, VehicleNotFoundException;
}
