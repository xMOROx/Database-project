package org.agh.edu.pl.carrentalrestapi.service;

import org.agh.edu.pl.carrentalrestapi.entity.Booking;
import org.agh.edu.pl.carrentalrestapi.exception.BookingUnavailableVehicleException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingService {
    Long addBooking(Booking booking) throws BookingUnavailableVehicleException;

    void cancelBooking(Long bookingId);

    void bookingRent(Long bookingId);

    void bookingReturn(Long bookingId);

    Page<Booking> getBookings(Pageable pageable);

    Page<Booking> getBookingsRented(Pageable pageable);

    Page<Booking> getBookingsReserved(Pageable pageable);

    Page<Booking> getBookingsCanceledForPage(Pageable pageable);

    Page<Booking> getBookingsReturned(Pageable pageable);

    Booking getBookingsById(Long bookingId);

    List<Booking> getAllBookings();

    Page<Booking> getUserBookings(PageRequest pageRequest, Long userId);

    Page<Booking> getUserBookingsReserved(PageRequest pageRequest, Long userId);

    Page<Booking> getUserBookingsRented(PageRequest pageRequest, Long userId);

}
