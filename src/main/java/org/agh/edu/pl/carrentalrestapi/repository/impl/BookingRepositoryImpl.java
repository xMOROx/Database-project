package org.agh.edu.pl.carrentalrestapi.repository.impl;

import jakarta.persistence.EntityManager;
import org.agh.edu.pl.carrentalrestapi.entity.Booking;
import org.agh.edu.pl.carrentalrestapi.entity.BookingStateCode;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.agh.edu.pl.carrentalrestapi.entity.VehicleStatus;
import org.agh.edu.pl.carrentalrestapi.exception.types.BookingUnavailableVehicleException;
import org.agh.edu.pl.carrentalrestapi.utils.enums.BookingStateCodeConstants;
import org.agh.edu.pl.carrentalrestapi.utils.enums.VehicleStatuses;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BookingRepositoryImpl {
    EntityManager entityManager;

    public BookingRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Long addBooking(Booking booking) throws BookingUnavailableVehicleException {
        Vehicle vehicle = entityManager.find(Vehicle.class, booking.getVehicle().getId());

        VehicleStatus vehicleStatus = vehicle.getVehicleStatus();
        String vehicleStatusType = vehicleStatus.getType();

        if (vehicleStatusType.equals(VehicleStatuses.UNA.toString())) {
            throw new BookingUnavailableVehicleException("Vehicle is unavailable");
        }

        VehicleStatus unavailableStatus = entityManager.find(VehicleStatus.class, VehicleStatuses.UNA.toString());

        vehicle.setVehicleStatus(unavailableStatus);

        entityManager.persist(vehicle);
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

}
