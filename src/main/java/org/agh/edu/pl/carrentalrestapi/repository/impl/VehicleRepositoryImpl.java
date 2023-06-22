package org.agh.edu.pl.carrentalrestapi.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.agh.edu.pl.carrentalrestapi.entity.Equipment;
import org.agh.edu.pl.carrentalrestapi.entity.Location;
import org.agh.edu.pl.carrentalrestapi.entity.Vehicle;
import org.agh.edu.pl.carrentalrestapi.entity.VehicleStatus;
import org.agh.edu.pl.carrentalrestapi.exception.types.*;
import org.agh.edu.pl.carrentalrestapi.model.VehicleAddModel;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class VehicleRepositoryImpl {

    EntityManager entityManager;

    public VehicleRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Transactional
    public Long addVehicle(VehicleAddModel vehicle)
            throws VehicleWithRegistrationExistsException, LocationNotFoundException, StatusForVehicleNotFoundException, EquipmentNotFoundException {

        if (findByRegistration(vehicle.getRegistration()).isPresent()) {
            throw new VehicleWithRegistrationExistsException(vehicle.getRegistration());
        }

        Location location = entityManager.find(Location.class, vehicle.getVehicleLocationId());

        if (location == null) {
            throw new LocationNotFoundException(vehicle.getVehicleLocationId());
        }


        VehicleStatus vehicleStatus = entityManager.find(VehicleStatus.class, vehicle.getVehicleStatusId());

        if (vehicleStatus == null) {
            throw new StatusForVehicleNotFoundException(vehicle.getVehicleStatusId());
        }

        Vehicle vehicleToAdd = new Vehicle(
                vehicle.getRegistration(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getPhotoURL(),
                vehicle.getBodyType(),
                vehicle.getProductionYear(),
                vehicle.getFuelType(),
                vehicle.getPower(),
                vehicle.getGearbox(),
                vehicle.getFrontWheelDrive(),
                vehicle.getDoorsNumber(),
                vehicle.getSeatsNumber(),
                vehicle.getColor(),
                vehicle.getMetalic(),
                vehicle.getDescription(),
                vehicle.getDailyFee(),
                vehicle.getBestOffer(),
                vehicleStatus,
                location
        );

        location.getVehicles().add(vehicleToAdd);

        entityManager.persist(vehicleToAdd);

        return vehicleToAdd.getId();
    }


    @Transactional
    public Optional<Vehicle> findByRegistration(String plateNumber) {
        TypedQuery<Vehicle> query = entityManager
                .createQuery("SELECT v FROM Vehicle v WHERE v.registration = :plateNumber", Vehicle.class);
        return query
                .setParameter("plateNumber", plateNumber)
                .getResultStream()
                .findFirst();
    }

    @Transactional
    public void addEquipmentToVehicle(Long id, Long equipmentId) throws VehicleNotFoundException, EquipmentNotFoundException, ParameterNotNullException {
        Vehicle vehicle = entityManager.find(Vehicle.class, id);

        if (vehicle == null) {
            throw new VehicleNotFoundException(id);
        }

        if (vehicle.getEquipment() == null) {
            throw new ParameterNotNullException("Vehicle equipment");
        }

        Equipment equipment = entityManager.find(Equipment.class, equipmentId);

        if (equipment == null) {
            throw new EquipmentNotFoundException(id);
        }

        vehicle.getEquipment().add(equipment);

        entityManager.persist(vehicle);
    }

    @Transactional
    public void removeEquipmentFromVehicle(Long id, Long equipmentId) throws VehicleNotFoundException, EquipmentNotFoundException {
        Vehicle vehicle = entityManager.find(Vehicle.class, id);

        if (vehicle == null) {
            throw new VehicleNotFoundException(id);
        }

        Equipment equipment = entityManager.find(Equipment.class, equipmentId);

        if (equipment == null) {
            throw new EquipmentNotFoundException(id);
        }

        vehicle.getEquipment().remove(equipment);

        entityManager.persist(vehicle);
    }

    @Transactional
    public void changeLocation(Long vehicleId, Long locationId) throws VehicleNotFoundException, LocationNotFoundException {
        Vehicle vehicle = entityManager.find(Vehicle.class, vehicleId);

        if (vehicle == null) {
            throw new VehicleNotFoundException(vehicleId);
        }

        Location location = entityManager.find(Location.class, locationId);

        if (location == null) {
            throw new LocationNotFoundException(locationId);
        }

        vehicle.getLocation().getVehicles().remove(vehicle);
        vehicle.setLocation(location);
        location.getVehicles().add(vehicle);

        entityManager.persist(vehicle);
        entityManager.persist(location);
    }

    @Transactional
    public void changeStatusForVehicle(Long vehicleId, Long statusId)
            throws VehicleNotFoundException, StatusForVehicleNotFoundException {

        Vehicle vehicle = entityManager.find(Vehicle.class, vehicleId);

        if (vehicle == null) {
            throw new VehicleNotFoundException(vehicleId);
        }

        VehicleStatus status = entityManager.find(VehicleStatus.class, statusId);

        if (status == null) {
            throw new StatusForVehicleNotFoundException(statusId);
        }

        vehicle.getVehicleStatus().getVehicles().remove(vehicle);
        vehicle.setVehicleStatus(status);
        status.getVehicles().add(vehicle);

        entityManager.persist(vehicle);
        entityManager.persist(status);
    }
}

