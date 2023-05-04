package org.agh.edu.pl.carrentalrestapi.repository.impl;

import jakarta.persistence.EntityManager;
import org.agh.edu.pl.carrentalrestapi.entity.Booking;
import org.agh.edu.pl.carrentalrestapi.entity.BookingStateCode;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.agh.edu.pl.carrentalrestapi.entity.VehicleStatus;
import org.agh.edu.pl.carrentalrestapi.exception.BookingUnavailableVehicleException;
import org.agh.edu.pl.carrentalrestapi.utils.BookingStatusCode;
import org.agh.edu.pl.carrentalrestapi.utils.VehicleStatuses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BookingRepositoryImpl {
    EntityManager entityManager;

    public BookingRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void addBooking(Booking booking) throws BookingUnavailableVehicleException {
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
    }

    @Transactional
    public void cancelBooking(Long bookingId) {
        Booking booking = entityManager.find(Booking.class, bookingId);
        Vehicle vehicle = entityManager.find(Vehicle.class, booking.getVehicle().getId());

        VehicleStatus availableStatus = entityManager.find(VehicleStatus.class, VehicleStatuses.AVI.toString());
        BookingStateCode bookingStatusCancel = entityManager.find(BookingStateCode.class, BookingStatusCode.CAN.toString());
        vehicle.setVehicleStatus(availableStatus);
        booking.setBookingStateCode(bookingStatusCancel);

        entityManager.persist(vehicle);
        entityManager.persist(booking);
    }

    @Transactional
    public void bookingRented(Long bookingId) {
        Booking booking = entityManager.find(Booking.class, bookingId);
        BookingStateCode bookingStatusRented = entityManager.find(BookingStateCode.class, BookingStatusCode.REN.toString());
        booking.setBookingStateCode(bookingStatusRented);
        entityManager.persist(booking);
    }

    @Transactional
    public void bookingReturn(Long bookingId) {
        Booking booking = entityManager.find(Booking.class, bookingId);
        Vehicle vehicle = entityManager.find(Vehicle.class, booking.getVehicle().getId());

        VehicleStatus availableStatus = entityManager.find(VehicleStatus.class, VehicleStatuses.AVI.toString());
        BookingStateCode bookingStatusReturn = entityManager.find(BookingStateCode.class, BookingStatusCode.RET.toString());

        vehicle.setVehicleStatus(availableStatus);
        booking.setBookingStateCode(bookingStatusReturn);

        entityManager.persist(vehicle);
        entityManager.persist(booking);
    }

}
