package org.agh.edu.pl.carrentalrestapi.repository;

import org.agh.edu.pl.carrentalrestapi.entity.Booking;
import org.agh.edu.pl.carrentalrestapi.entity.BookingStateCode;
import org.agh.edu.pl.carrentalrestapi.exception.BookingUnavailableVehicleException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    void addBooking(Booking booking) throws BookingUnavailableVehicleException;

    void cancelBooking(Long bookingId);

    void bookingRented(Long bookingId);

    void bookingReturn(Long bookingId);
    @Query("SELECT b FROM Booking b WHERE b.bookingStateCode=:bookingStateCode")
    @Transactional
    Page<Booking> findByBookingStateCode(BookingStateCode bookingStateCode, Pageable pageable);
    @Query("SELECT b FROM Booking b WHERE b.bookingStateCode=:bookingStateCode AND b.user.id=:userId")
    @Transactional
    Page<Booking> findByBookingStateCodeAndUserId(BookingStateCode bookingStateCode, Long userId, Pageable pageable);
}
