package org.agh.edu.pl.carrentalrestapi.repository;

import org.agh.edu.pl.carrentalrestapi.entity.Booking;
import org.agh.edu.pl.carrentalrestapi.entity.BookingStateCode;
import org.agh.edu.pl.carrentalrestapi.exception.BookingUnavailableVehicleException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    void addBooking(Booking booking) throws BookingUnavailableVehicleException;

    void cancelBooking(Long bookingId);

    void bookingRented(Long bookingId);

    void bookingReturn(Long bookingId);

    Page<Booking> getBookingByBookingStateCode(BookingStateCode bookingStateCode, Pageable pageable);

    Page<Booking> getBookingsByBookingStateCodeAndUserId(BookingStateCode bookingStateCode, Long userId, Pageable pageable);
}
