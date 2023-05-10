package org.agh.edu.pl.carrentalrestapi.repository;

import org.agh.edu.pl.carrentalrestapi.entity.Booking;
import org.agh.edu.pl.carrentalrestapi.exception.types.BookingUnavailableVehicleException;
import org.agh.edu.pl.carrentalrestapi.utils.enums.BookingStateCodeConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    void addBooking(Booking booking) throws BookingUnavailableVehicleException;

    void cancelBooking(Long bookingId);

    void bookingRent(Long bookingId);

    void bookingReturn(Long bookingId);
    @Query(value = "SELECT b FROM Booking b WHERE b.bookingStateCode=:bookingStateCode",
            countQuery = "SELECT count(b) FROM Booking b WHERE b.bookingStateCode=:bookingStateCode")
    @Transactional
    Page<Booking> findByBookingStateCode(BookingStateCodeConstants bookingStateCode, Pageable pageable);
    @Query(value = "SELECT b FROM Booking b WHERE b.bookingStateCode=:bookingStateCode AND b.user.id=:userId",
            countQuery = "SELECT count(b) FROM Booking b WHERE b.bookingStateCode=:bookingStateCode AND b.user.id=:userId")
    @Transactional
    Page<Booking> findByUserIdAndBookingStateCode(Long userId, BookingStateCodeConstants bookingStateCode,  Pageable pageable);
    @Query(value = "SELECT b FROM Booking b WHERE b.user.id=:userId",
            countQuery = "SELECT count(b) FROM Booking b WHERE b.user.id=:userId")
    Page<Booking> findByUserId(Long userId, PageRequest pageRequest);
}
