package org.agh.edu.pl.carrentalrestapi.repository;

import org.agh.edu.pl.carrentalrestapi.entity.Booking;
import org.agh.edu.pl.carrentalrestapi.exception.types.BookingUnavailableVehicleException;
import org.agh.edu.pl.carrentalrestapi.exception.types.LocationNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.types.UserNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.types.VehicleNotFoundException;
import org.agh.edu.pl.carrentalrestapi.model.ReserveVehicleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {
    Long addBooking(ReserveVehicleModel reservation) throws BookingUnavailableVehicleException, VehicleNotFoundException, UserNotFoundException, LocationNotFoundException;

    void cancelBooking(Long bookingId);

    void bookingRent(Long bookingId);

    void bookingReturn(Long bookingId);

    @Query(value = "SELECT b FROM Booking b WHERE b.bookingStateCode.bookingCode=:bookingStateCode",
            countQuery = "SELECT count(b) FROM Booking b WHERE b.bookingStateCode.bookingCode=:bookingStateCode")
    @Transactional
    Page<Booking> findByBookingStateCode(String bookingStateCode, Pageable pageable);

    @Query(value = "SELECT b FROM Booking b WHERE b.bookingStateCode.bookingCode=:bookingStateCode AND b.user.id=:userId",
            countQuery = "SELECT count(b) FROM Booking b WHERE b.bookingStateCode.bookingCode=:bookingStateCode AND b.user.id=:userId")
    @Transactional
    Page<Booking> findByUserIdAndBookingStateCode(Long userId, String bookingStateCode, Pageable pageable);

    @Query(value = "SELECT b FROM Booking b WHERE b.user.id=:userId",
            countQuery = "SELECT count(b) FROM Booking b WHERE b.user.id=:userId")
    Page<Booking> findByUserId(Long userId, Pageable pageRequest);

    @Query(value = "SELECT b FROM Booking b WHERE b.bookingStateCode.bookingCode='REN' " +
            "AND b.returnDate >= CURRENT_DATE",
            countQuery = "SELECT count(b) FROM Booking b WHERE b.bookingStateCode.bookingCode='REN' " +
                    "AND b.returnDate >= CURRENT_DATE")
    Page<Booking> findActiveBookings(Pageable pageRequest);

    @Query(value = "SELECT b FROM Booking b WHERE b.bookingStateCode.bookingCode='REN' " +
            "AND b.returnDate >= CURRENT_DATE AND b.user.id=:userId",
            countQuery = "SELECT count(b) FROM Booking b WHERE b.bookingStateCode.bookingCode='REN' " +
                    "AND b.returnDate >= CURRENT_DATE AND b.user.id=:userId")
    Page<Booking> findActiveBookingsByUserId(Long userId, Pageable pageRequest);

}
