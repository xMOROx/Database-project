package org.agh.edu.pl.carrentalrestapi.repository.impl;

import jakarta.persistence.EntityManager;
import org.agh.edu.pl.carrentalrestapi.entity.*;
import org.agh.edu.pl.carrentalrestapi.exception.types.BookingUnavailableVehicleException;
import org.agh.edu.pl.carrentalrestapi.exception.types.LocationNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.types.UserNotFoundException;
import org.agh.edu.pl.carrentalrestapi.exception.types.VehicleNotFoundException;
import org.agh.edu.pl.carrentalrestapi.model.ReserveVehicleModel;
import org.agh.edu.pl.carrentalrestapi.utils.enums.BookingStateCodeConstants;
import org.agh.edu.pl.carrentalrestapi.utils.enums.VehicleStatuses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class BookingRepositoryImpl {
    EntityManager entityManager;

    public BookingRepositoryImpl(EntityManager entityManager
    ) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Long addBooking(ReserveVehicleModel reservation) throws BookingUnavailableVehicleException, VehicleNotFoundException, UserNotFoundException, LocationNotFoundException {
        Long vehicleID = reservation.getVehicleID();
        Vehicle vehicle = entityManager.find(Vehicle.class, vehicleID);

        if (vehicle == null) {
            throw new VehicleNotFoundException(vehicleID);
        }

        if (!vehicle.getVehicleStatus().getType().equals(VehicleStatuses.AVI.toString())) {
            throw new BookingUnavailableVehicleException(vehicleID);
        }

        Location location = entityManager.find(Location.class, reservation.getLocationID());

        if (location == null) {
            throw new LocationNotFoundException(reservation.getLocationID());
        }

        LocalDateTime receiptDate;
        LocalDateTime returnDate = LocalDateTime.parse(convertDate(reservation.getReturnDate()));
        String date = reservation.getReceiptDate();

        if (Objects.isNull(date)) {
            receiptDate = LocalDateTime.now();
        } else {
            String replacedDate = convertDate(date);
            receiptDate = LocalDateTime.parse(replacedDate);
        }


        if (!this.isVehicleAvailableToRent(vehicleID, receiptDate, returnDate)) {
            throw new BookingUnavailableVehicleException(vehicleID);
        }


        User user = entityManager.find(User.class, reservation.getUserID());
//        TODO: uncomment when user service will be ready
//        if (user == null) {
//            throw new UserNotFoundException(reservation.getUserID());
//        }


        String query = "SELECT b FROM BookingStateCode b WHERE b.bookingCode=:bookingCode";

        BookingStateCode bookingStatus = entityManager.createQuery(query, BookingStateCode.class)
                .setParameter("bookingCode", BookingStateCodeConstants.RES.toString())
                .getSingleResult();


        Booking booking = new Booking(
                user,
                vehicle,
                location,
                bookingStatus,
                receiptDate,
                returnDate,
                reservation.getTotalCost());

        entityManager.persist(booking);

        return booking.getId();
    }

    @Transactional
    public void cancelBooking(Long bookingId) {
        Booking booking = entityManager.find(Booking.class, bookingId);
        Vehicle vehicle = entityManager.find(Vehicle.class, booking.getVehicle().getId());

        VehicleStatus availableStatus = entityManager.find(VehicleStatus.class, VehicleStatuses.AVI.toString());
        BookingStateCode bookingStatusCancel = entityManager.find(BookingStateCode.class, BookingStateCodeConstants.CAN.toString());
        vehicle.setVehicleStatus(availableStatus);
        booking.setBookingStateCode(bookingStatusCancel);

        entityManager.persist(vehicle);
        entityManager.persist(booking);
    }

    @Transactional
    public void bookingRent(Long bookingId) {
        Booking booking = entityManager.find(Booking.class, bookingId);
        BookingStateCode bookingStatusRented = entityManager.find(BookingStateCode.class, BookingStateCodeConstants.REN.toString());
        booking.setBookingStateCode(bookingStatusRented);
        entityManager.persist(booking);
    }

    @Transactional
    public void bookingReturn(Long bookingId) {
        Booking booking = entityManager.find(Booking.class, bookingId);
        Vehicle vehicle = entityManager.find(Vehicle.class, booking.getVehicle().getId());

        VehicleStatus availableStatus = entityManager.find(VehicleStatus.class, VehicleStatuses.AVI.toString());
        BookingStateCode bookingStatusReturn = entityManager.find(BookingStateCode.class, BookingStateCodeConstants.RET.toString());

        vehicle.setVehicleStatus(availableStatus);
        booking.setBookingStateCode(bookingStatusReturn);

        entityManager.persist(vehicle);
        entityManager.persist(booking);
    }

    private boolean isVehicleAvailableToRent(Long vehicleId,
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        String query = "SELECT DISTINCT v FROM Vehicle v " +
        " WHERE v.id = :vehicleId AND  v.vehicleStatus.type='AVI' AND v.id NOT IN (SELECT b.vehicle.id FROM Booking b WHERE b.receiptDate <= :endDate AND b.returnDate >= :startDate " +
                "AND b.bookingStateCode.bookingCode IN ('RES', 'REN'))";


        return entityManager.createQuery(query, Booking.class)
                .setParameter("vehicleId", vehicleId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList()
                .size() > 0;
    }

    private String convertDate(String date) {
        if(date.matches("^[1-9][0-9]{3}-[0-9]{2}-[0-9]{2}$")) return date.trim() + "T23:59:59";

        return date.trim().replace(" ", "T");
    }
}
